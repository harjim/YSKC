package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.PatentFileDao;
import com.yskc.ms.dao.rs.RsPatentDao;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.entity.rs.PatentFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.CustomerSelectModel;
import com.yskc.ms.models.patent.PatentBuyingDemandModel;
import com.yskc.ms.models.patentPlan.*;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import com.yskc.ms.models.projectAudit.FlowInstanceLogModel;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.ms.PatentPlanService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 10:54
 * @Description:
 */
@Service
public class PatentPlanServiceImpl implements PatentPlanService {

    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private RsProjectDao projectDao;
    @Autowired
    private RsPatentDao patentDao;
    @Autowired
    private PatentFileDao patentFileDao;
    @Autowired
    private FlowInstancePatentDao flowInstancePatentDao;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private PatentAuditDao patentAuditDao;
    @Autowired
    private PatentPlanProcessDao patentPlanProcessDao;
    @Autowired
    private PatentPlanInfoDao patentPlanInfoDao;
    @Autowired
    private CustomerDao customerDao;


    @Override
    public List<CustomerSelectModel> getCustomers(String companyName) {
        return patentPlanDao.getCustomers(companyName);
    }

    @Override
    public Boolean save(PatentPlanModel model, UserInfo userInfo) throws OwnerException {
        if (null == model) {
            throw new OwnerException("保存数据异常！");
        }
        Date date = new Date();
        PatentPlanEntity patentPlan;
        if (null == model.getId()) {
            patentPlan = new PatentPlanEntity();
            BeanUtils.copyProperties(model, patentPlan);
            patentPlan.create(userInfo.getId(), date);
            patentPlanDao.insert(patentPlan);
        } else {
            patentPlan = patentPlanDao.selectById(model.getId());
            List<PatentAuditEntity> audits = patentAuditDao.getByPatent(Arrays.asList(model.getId()));
            if (patentPlan.getProcess() != 0) {
                throw new OwnerException("不能修改已提交的专利申请！");
            } else {
                if (!CollectionUtils.isEmpty(audits) && !FlowInstanceStatusEnum.canModify(audits.get(0).getStatus())) {
                    throw new OwnerException("不能修改已提交的专利申请！");
                }
            }
            BeanUtils.copyProperties(model, patentPlan);
            patentPlanDao.updateById(patentPlan);
        }
        if (CollectionUtils.isEmpty(model.getFileModels())) {
            throw new OwnerException("请先上传交底书后再保存！");
        }
        List<PatentFileEntity> files = new ArrayList<>();
        List<Integer> existIds = new ArrayList<>();
        for (PatentFileModel fileModel : model.getFileModels()) {
            if (fileModel.getId() != null) {
                existIds.add(fileModel.getId());
            } else {
                files.add(new PatentFileEntity(patentPlan.getId(), fileModel.getFileName(), fileModel.getFilePath(), fileModel.getFileType(), userInfo, null));
            }
        }
        patentFileDao.delFile(existIds, patentPlan.getId(), 0); // 0交底书
        if (!CollectionUtils.isEmpty(files)) {
            patentFileDao.addBatch(files);
        }
        return true;
    }

    @Override
    public PageModel<List<PatentPlanModel>> getList(QueryPatentPlanModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pp.createTime");
            page.setDescs(desc);
        }
        List<PatentPlanModel> models = patentPlanDao.getList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(models)) {
            List<Integer> projectIds = models.stream().filter(e -> e.getProjectId() != -1).map(e -> e.getProjectId()).distinct().collect(Collectors.toList());
            Map<Integer, String> rdMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(projectIds)) {
                List<ProjectEntity> projects = projectDao.selectBatchIds(projectIds);
                rdMap = projects.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getRdTitle()));
            }
            for (PatentPlanModel item : models) {
                if (rdMap.containsKey(item.getProjectId())) {
                    item.setRdTitle(rdMap.get(item.getProjectId()));
                }
                if(Objects.equals(item.getStatus(),2)){
                    item.setNodeNumber(null);
                }
            }
            List<Integer> groupIds = models.stream().filter(a -> a.getGroupId() > 0).map(a -> a.getGroupId()).distinct().collect(Collectors.toList());
            Map<Integer, String> groupMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(groupIds)) {
                List<MiniModel> groups = customerDao.getGroupList(groupIds);
                groups.forEach(item -> groupMap.put(item.getId(), item.getTitle()));
            }
            if (!CollectionUtils.isEmpty(groupMap)) {
                for (PatentPlanModel model : models) {
                    model.setGroupName(groupMap.get(model.getGroupId()));
                }
            }
        }
        return PageUtils.build(page, models);
    }

    @Override
    public Boolean del(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("请选择要删除的专利申请！");
        }
        List<PatentPlanEntity> patentPlans = patentPlanDao.selectBatchIds(ids);
        List<PatentAuditEntity> audits = patentAuditDao.getByPatent(ids);
        Map<Integer, PatentAuditEntity> auditMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(audits)) {
            auditMap = audits.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        }
        for (PatentPlanEntity patentPlan : patentPlans) {
            if (patentPlan.getProcess() != 0) {
                throw new OwnerException("申请名称：" + patentPlan.getPatentName() + "已提交审核，不能删除！");
            } else {
                if (auditMap.containsKey(patentPlan.getId()) && !FlowInstanceStatusEnum.canModify(auditMap.get(patentPlan.getId()).getStatus())) {
                    throw new OwnerException("申请名称：" + patentPlan.getPatentName() + "已提交审核，不能删除！");
                }
            }
        }
        return patentPlanDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Map<String, Object> getInfo(Integer patentPlanId, String patentNo, Integer msUserId) {
        Map<String, Object> resultMap = new HashMap<>();
        if (!StringUtils.isEmpty(patentNo)) {
            PatentInfoModel infoModel = new PatentInfoModel();
            RsPatentEntity entity = patentDao.queryPatentByNo(patentNo);
            PatentPlanInfoEntity infoEntity = patentPlanInfoDao.selectByPatentId(patentPlanId);
            if (entity != null) {
                BeanUtils.copyProperties(entity, infoModel);
                if (null != infoEntity && null != infoEntity.getId()){
                    resultMap.put("patentPlanInfo", infoEntity);
                }
            }
            resultMap.put("patentInfo", infoModel);
        }
        Map<Integer, List<PatentFileModel>> dataMap = new HashMap<>();
        List<PatentFileModel> fileModels = patentFileDao.getPatentFiles(patentPlanId, patentNo);
        if (!CollectionUtils.isEmpty(fileModels)) {
            for (PatentFileModel model : fileModels) {
                if (!dataMap.containsKey(model.getFileType())) {
                    List<PatentFileModel> files = new ArrayList<>();
                    dataMap.put(model.getFileType(), files);
                }
                dataMap.get(model.getFileType()).add(model);
            }
        }
        resultMap.put("patentFiles", dataMap);
        Boolean auditPermission = false;
        AuditStatusModel patent = flowInstancePatentDao.getAuditStatus(patentPlanId);
        if (patent != null) {
            resultMap.put("instanceId", patent.getInstanceId());
            FlowInstanceLogModel log = flowInstanceLogDao.getLastLog(patent.getInstanceId());
            if (log != null) {
                resultMap.put("lastLog", log.getSuggestion());
                resultMap.put("lastLogTime", log.getCreateTime());
            }
            FlowInstance flowInstance = flowInstancePatentDao.getUserAudit(msUserId, patent.getInstanceId());
            if (flowInstance != null) {
                auditPermission = true;
            }
        }
        resultMap.put("auditPermission", auditPermission);
        List<PatentPlanProcess> processes = patentPlanProcessDao.getByPatent(Arrays.asList(patentPlanId));
        if (!CollectionUtils.isEmpty(processes)) {
            Map<String, PatentPlanProcess> map = processes.stream().collect(Collectors.toMap(e -> e.getProcess().toString(), e -> e, (o, n) -> n));
            resultMap.putAll(map);
        }
        return resultMap;
    }

    @Override
    public Boolean savePatent(PatentInfoModel model, UserInfo userInfo) throws OwnerException {
        if (null == model || null == model.getPatentPlanId()) {
            throw new OwnerException("数据异常，请联系管理员!");
        }
        Date date = new Date();
        PatentPlanModel patentPlanModel = patentPlanDao.getInfo(model.getPatentPlanId());
        List<RsPatentEntity> patents = patentDao.checkPatentNo(model.getPatentNo(), model.getId());
        if (!CollectionUtils.isEmpty(patents)) {
            throw new OwnerException("专利号已存在，不能重复添加！");
        }
        if (null == model.getId()) {
            RsPatentEntity patent = new RsPatentEntity();
            BeanUtils.copyProperties(model, patent);
            // source=2表示申请
            patent.setSource(2);
            patent.setCompanyId(patentPlanModel.getCompanyId());
            patent.setProjectId(patentPlanModel.getProjectId());
            patent.setYear(patentPlanModel.getYear());
            patent.create(-1, userInfo.getId(), date);
            patentDao.insert(patent);
            PatentPlanProcess planProcess = new PatentPlanProcess(patent.getId(), userInfo.getId(), date, 0, model.getRemark());
            patentPlanProcessDao.insert(planProcess);
            patentFileDao.updatePatentNo(model.getPatentPlanId(), model.getPatentNo(), -1, userInfo.getId(), date);
        } else {
            RsPatentEntity patent = patentDao.selectById(model.getId());
            if (!patent.getPatentNo().equals(model.getPatentNo())) {
                patentFileDao.updatePatentNo(model.getPatentPlanId(), model.getPatentNo(), -1, userInfo.getId(), date);
                patentFileDao.updatePatent(patent.getPatentNo(), model.getPatentNo(), -1, userInfo.getId(), date);
            }
            BeanUtils.copyProperties(model, patent);
            patent.update(-1, userInfo.getId(), date);
            patentDao.updateById(patent);
        }
        patentPlanDao.updatePatentNo(model.getPatentNo(), model.getPatentPlanId(), date, userInfo.getId(),null);
        return true;
    }

    @Override
    public List<RsProjectBaseModel> getProjectSelect(Integer companyId, Integer year) {
        List<RsProjectBaseModel> models = projectDao.getProjectSelect(companyId, year);
        return models;
    }

    @Override
    public Boolean checkPatentNo(String patentNo, Integer patentId) throws OwnerException {
        List<RsPatentEntity> patents = patentDao.checkPatentNo(patentNo, patentId);
        if (!CollectionUtils.isEmpty(patents)) {
            throw new OwnerException("专利号已存在，不能重复添加！");
        }
        return true;
    }

    @Override
    public Boolean setProcess(PatentPlanModel model, UserInfo userInfo) throws OwnerException {
        PatentPlanModel patentPlan = patentPlanDao.getInfo(model.getId());
        Integer process = model.getProcess();
        if (patentPlan.getStatus() != 1 || process > 4) {
            throw new OwnerException("专利申请未完成审批或已授权，操作失败！");
        }
        Date date = new Date();
        PatentPlanEntity patentPlanEntity = patentPlanDao.selectById(model.getId());
        patentPlanEntity.setProcess(model.getProcess());
        patentPlanEntity.setLastUpdateTime(date);
        patentPlanEntity.setLastUpdatorId(userInfo.getId());
        PatentPlanProcess planProcess = new PatentPlanProcess(patentPlan.getId(), userInfo.getId(), date, model.getProcess(), model.getRemark());
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            patentPlanProcessDao.insert(planProcess);
            patentPlanDao.updateById(patentPlanEntity);
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());

        }
        return true;
    }

    @Override
    public Boolean submitPatent(PatentPlanModel model, UserInfo userInfo) throws OwnerException {
        if (null == model || null == model.getId()) {
            throw new OwnerException("数据异常，提交失败！");
        }
        PatentPlanModel patent = patentPlanDao.getInfo(model.getId());
        if (null == patent) {
            throw new OwnerException("专利申请已删除或不存在，提交失败");
        }
        if (null == patent.getCompanyId()) {
            throw new OwnerException("未开通优赛云系统，,请找业务确定氚云是否走完该客户的\"项目审批\"的流程");
        }
        if (patent.getProcess() > 0) {
            throw new OwnerException("专利申请审批已完成，不能提交！");
        }
        Date date = new Date();
        List<PatentAuditEntity> audits = patentAuditDao.getByPatent(Arrays.asList(model.getId()));
        if (!CollectionUtils.isEmpty(audits) && !FlowInstanceStatusEnum.canModify(audits.get(0).getStatus())) {
            throw new OwnerException("专利申请已提交，不能再次提交！");
        }
        Integer userId = userInfo.getId();
        if (CollectionUtils.isEmpty(audits)) {
            PatentAuditEntity auditEntity = new PatentAuditEntity(patent.getCompanyId(), model.getId(), userId, date);
            patentAuditDao.insert(auditEntity);
        } else {
            PatentAuditEntity auditEntity = audits.get(0);
            auditEntity.setStatus(4);
            auditEntity.setLastUpdateTime(date);
            auditEntity.setLastUpdatorId(userInfo.getId());
            patentAuditDao.updateById(auditEntity);
        }
        kafkaQueueService.submitAudit(CollUtil.newArrayList(model.getId()),userId, FlowModuleTypeEnum.RD_PATENT);
        return true;
    }

    @Override
    public PatentPlanEntity getById(Integer patentId, String patentNo) {
        return patentPlanDao.getById(patentId, patentNo);
    }

    @Override
    public PageModel<List<PatentPlanModel>> getPatentList(QueryAuditDataModel query, Integer userId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pp.createTime");
            page.setDescs(descs);
        }
        List<PatentPlanModel> patents = patentPlanDao.queryList(page, query);
        if (!CollectionUtils.isEmpty(patents)) {
            List<Integer> projectIds = patents.stream().filter(e -> e.getProjectId() > 0).map(e -> e.getProjectId()).collect(Collectors.toList());
            Map<Integer, String> projectMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(projectIds)) {
                List<ProjectEntity> projects = projectDao.selectBatchIds(projectIds);
                projectMap = projects.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getRdTitle()));
            }
            List<Integer> ids = patents.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<PatentFileModel> files = patentFileDao.getFiles(ids);
            Map<Integer, List<PatentFileModel>> fileMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(files)) {
                for (PatentFileModel file : files) {
                    if (!fileMap.containsKey(file.getPatentPlanId())) {
                        List<PatentFileModel> models = new ArrayList<>();
                        fileMap.put(file.getPatentPlanId(), models);
                    }
                    fileMap.get(file.getPatentPlanId()).add(file);
                }
            }
            List<FlowInstancePatent> patentInstances = flowInstancePatentDao.getPatents(ids, userId);
            Map<Integer, FlowInstancePatent> userPermissionMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patentInstances)) {
                userPermissionMap = patentInstances.stream().collect(Collectors.toMap(e -> e.getPatentPlanId(), e -> e));
            }

            for (PatentPlanModel model : patents) {
                Boolean hasPermission = false;
                if (projectMap.containsKey(model.getProjectId())) {
                    model.setRdTitle(projectMap.get(model.getProjectId()));
                }
                model.setFileModels(fileMap.get(model.getId()));
                if (userPermissionMap.containsKey(model.getId())) {
                    hasPermission = true;
                }
                model.setHasPermission(hasPermission);
            }
        }
        return PageUtils.build(page, patents);
    }

    @Override
    public PatentPlanModel getPatentInfo(Integer patentPlanId) throws OwnerException{
        PatentPlanModel model = patentPlanDao.getPatent(patentPlanId);
        if (model != null) {
            List<PatentFileModel> files = patentFileDao.getFiles(Arrays.asList(patentPlanId));
            if (!CollectionUtils.isEmpty(files)) {
                model.setFileModels(files);
            }
            if (model.getProjectId() > 0) {
                ProjectEntity project = projectDao.selectById(model.getProjectId());
                model.setRdTitle(project.getRdTitle());
            }
            Integer nodeNumber = patentPlanDao.getNodeNumber(model.getId());
            /*if (null == nodeNumber) {
                throw new OwnerException("获取审核节点状态失败，请联系管理员！");
            }*/
            model.setNodeNumber(nodeNumber);
        }
        return model;
    }

    @Override
    public Map<Integer, List<PatentFileModel>> getPatentFiles(Integer patentPlanId, String patentNo) {
        Map<Integer, List<PatentFileModel>> dataMap = new HashMap<>();
        List<PatentFileModel> fileModels = patentFileDao.getPatentFiles(patentPlanId, patentNo);
        if (!CollectionUtils.isEmpty(fileModels)) {
            for (PatentFileModel model : fileModels) {
                ToolUtil.putAndAdd(dataMap, model.getFileType(), model);
            }
        }
        return dataMap;
    }

    @Override
    public PatentPlanModel getById(Integer patentId) {
        return patentPlanDao.getInfo(patentId);
    }

    @Override
    public Boolean setEngineer(SetPatentEngineerModel setEngineer, Integer userId) {
        return patentPlanDao.updateEngineer(setEngineer.getIds(), setEngineer.getEngineerId(), new Date(), userId) > 0;
    }

    @Override
    public Boolean setMaster(PatentPlanEntity patentPlan, Integer userId) throws OwnerException {
        Integer patentPlanId = patentPlan.getId();
        Integer masterId = patentPlan.getMasterId();
        if (null == patentPlanId || null == masterId) {
            throw new OwnerException("设置专利代理人失败。");
        }
        return patentPlanDao.updateMaster(Arrays.asList(patentPlanId),masterId,new Date(),userId) > 0;
    }
}
