package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.RsPatentDao;
import com.yskc.ms.dao.rs.RsPatentPlanDao;
import com.yskc.ms.entity.ms.FlowInstancePatent;
import com.yskc.ms.entity.ms.PatentMasterEntity;
import com.yskc.ms.entity.ms.PatentPlanEntity;
import com.yskc.ms.entity.rs.PatentOpinionEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.CustomerModel;
import com.yskc.ms.models.patent.FlowPatentModel;
import com.yskc.ms.models.patentPlan.ChangePatentNoModel;
import com.yskc.ms.models.patentPlan.PlanToPatentModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.patentPlan.RsPatentPlanModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.rs.RsPatentPlanService;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/7 8:20
 * description:
 */
@Service
public class RsPatentPlanServiceImpl implements RsPatentPlanService {

    @Autowired
    private RsPatentPlanDao rsPatentPlanDao;
    @Autowired
    private PatentMasterDao patentMasterDao;
    @Autowired
    private RsPatentDao rsPatentDao;
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private FlowInstancePatentDao flowInstancePatentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PatentPlanDao patentPlanDao;


    @Override
    public PageModel<List<RsPatentPlanModel>> queryPatentPlans(QueryPatentPlanModel model, UserInfo userInfo, DataPermModel perm) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pp.createTime");
            page.setDescs(desc);
        }
        List<FlowInstancePatent> instancePatents = flowInstancePatentDao.getPassInstances();
        if (CollectionUtils.isEmpty(instancePatents)) {
            return PageUtils.build(page, new ArrayList<>());
        }
        List<Integer> patentIds = instancePatents.stream().map(e -> e.getPatentPlanId()).distinct().collect(Collectors.toList());
        List<RsPatentPlanModel> patentPlanModels = rsPatentPlanDao.queryPatentPlanList(model, page, perm, patentIds);
        if (!CollectionUtils.isEmpty(patentPlanModels)) {
            List<PatentOpinionEntity> opinions = rsPatentPlanDao.getOpinions(patentIds);
            Map<Integer, String> opinionMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(opinions)) {
                opinionMap = opinions.stream().collect(Collectors.toMap(e -> e.getPatentPlanId(), e -> e.getOpinion()));
            }
            //获取负责人id列表
            List<Integer> masterIds = new ArrayList<>();
            List<Integer> companyIds = new ArrayList<>();
            Set<Integer> msCreatorIds = new HashSet<>();
            for (RsPatentPlanModel planModel : patentPlanModels) {
                if (planModel.getMasterId() != null) {
                    masterIds.add(planModel.getMasterId());
                }
                companyIds.add(planModel.getCompanyId());
                msCreatorIds.add(planModel.getMsCreatorId());
            }
            List<CustomerModel> customers = customerDao.getByCompanyIds(companyIds);
            Map<Integer, CustomerModel> customerMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(customers)) {
                customerMap = customers.stream().collect(Collectors.toMap(e -> e.getCompanyId(), e -> e));
            }
            Map<Integer, String> masterMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(masterIds)) {
                List<PatentMasterEntity> patentMasterEntities = patentMasterDao.getListByIds(masterIds);
                masterMap = patentMasterEntities.stream().collect(Collectors.toMap(PatentMasterEntity::getId, PatentMasterEntity::getMasterName));
            }
            Map<Integer, String> realNames = new HashMap<>();
            List<MiniUserModel> users = userDao.getUsers(msCreatorIds);
            if (!CollectionUtils.isEmpty(users)) {
                users.forEach(a -> realNames.put(a.getId(), a.getRealName()));
            }
            for (RsPatentPlanModel patentModel : patentPlanModels) {
                if (patentModel.getMasterId() != null && patentModel.getMasterId() != -1) {
                    patentModel.setMasterName(masterMap.get(patentModel.getMasterId()));
                }
                if (customerMap.containsKey(patentModel.getCompanyId())) {
                    CustomerModel customerModel = customerMap.get(patentModel.getCompanyId());
                    patentModel.setOwner(customerModel.getOwner());
                    patentModel.setDeptName(customerModel.getDeptName());
                }
                patentModel.setCreator(realNames.get(patentModel.getMsCreatorId()));
                String path = patentModel.getPath();
                String docName = path.substring(path.lastIndexOf("/") + 14);
                patentModel.setDocName(docName);
                if (opinionMap.containsKey(patentModel.getId())) {
                    patentModel.setOpinion(opinionMap.get(patentModel.getId()));
                }
            }
        }
        return PageUtils.build(page, patentPlanModels);
    }

    @Override
    public Boolean allocationMaster(List<Integer> ids, UserInfo userInfo, Integer masterId) throws OwnerException {
        PatentMasterEntity masterEntity = patentMasterDao.selectById(masterId);
        if (masterEntity == null) {
            throw new OwnerException("负责人不存在，无法分配");
        }
        List<FlowPatentModel> models = flowInstancePatentDao.getAuditInfo(ids);
        Map<Integer, FlowPatentModel> passAuditMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            passAuditMap = models.stream().collect(Collectors.toMap(e -> e.getPatentPlanId(), e -> e));
        }
        List<RsPatentPlanEntity> planEntities = rsPatentPlanDao.selectBatchIds(ids);
        for (RsPatentPlanEntity plan : planEntities) {
            if (!passAuditMap.containsKey(plan.getId())) {
                throw new OwnerException("申请专利尚未完成审核，不能分配负责人！");
            }
            plan.setMasterId(masterId);
            plan.setMsLastUpdatorId(userInfo.getId());
            plan.setLastUpdateTime(new Date());
            plan.setStatus(1);
        }
        return rsPatentPlanDao.updateList(planEntities) > 0;
    }

    @Override
    public Boolean updateStatus(PlanToPatentModel model, UserInfo userInfo) throws OwnerException {
        RsPatentPlanEntity patentPlanEntity = rsPatentPlanDao.selectById(model.getId());
        if (patentPlanEntity == null) {
            throw new OwnerException("专利立项不存在，无法修改");
        }
        //专利立项数据修改
        patentPlanEntity.setStatus(2);
        patentPlanEntity.setPatentNo(model.getPatentNo());
        patentPlanEntity.setLastUpdateTime(new Date());
        patentPlanEntity.setLastUpdatorId(-1);
        patentPlanEntity.setMsLastUpdatorId(userInfo.getId());
        //添加专利
        RsPatentEntity rsPatentEntity = new RsPatentEntity();
        rsPatentEntity.setProjectId(patentPlanEntity.getProjectId());
        rsPatentEntity.setPatentNo(model.getPatentNo());
        rsPatentEntity.setPatentName(model.getPatentName());
        rsPatentEntity.setMainType(model.getMainType());
        rsPatentEntity.setCompanyId(patentPlanEntity.getCompanyId());
        rsPatentEntity.setInventor(model.getInventor() != null ? model.getInventor() : "");
        rsPatentEntity.setApplyDateTime(model.getApplyDateTime());
        rsPatentEntity.setMsCreatorId(userInfo.getId());
        rsPatentEntity.setMsLastUpdatorId(userInfo.getId());
        rsPatentEntity.setCreateTime(new Date());
        rsPatentEntity.setLastUpdateTime(new Date());
        rsPatentEntity.setCreatorId(-1);
        rsPatentEntity.setLastUpdatorId(-1);
        rsPatentEntity.setSource(0);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsPatentPlanDao.updateById(patentPlanEntity);
            rsPatentDao.insert(rsPatentEntity);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public RsPatentPlanEntity getById(Integer id, String patentNo) {
        RsPatentPlanEntity rsPatentPlanEntity = rsPatentPlanDao.getPatent(id, patentNo);
        return rsPatentPlanEntity;
    }

    @Override
    public Boolean rejectPatent(List<RsPatentPlanModel> models, UserInfo userInfo) throws OwnerException {
        if (!CollectionUtils.isEmpty(models)) {
            List<Integer> ids = models.stream().map(RsPatentPlanModel::getId).collect(Collectors.toList());
            List<RsPatentPlanEntity> planEntities = rsPatentPlanDao.selectBatchIds(ids);
            for (RsPatentPlanEntity entity : planEntities) {
                if (entity.getStatus() != 1) {
                    throw new OwnerException("只允许驳回申请中状态的专利立项");
                }
            }
            return rsPatentPlanDao.rejectPatent(ids, 3, new Date(), userInfo.getId()) > 0;
        }
        return false;
    }

    @Override
    public Boolean updatePatentNo(ChangePatentNoModel model, UserInfo userInfo) throws OwnerException {
        RsPatentPlanEntity rsPatentPlanEntity = rsPatentPlanDao.selectById(model.getId());
        if (rsPatentPlanEntity == null) {
            throw new OwnerException("未查询到要修改的专利立项");
        }
        RsPatentEntity patentEntity = rsPatentDao.queryPatentByNo(rsPatentPlanEntity.getPatentNo());
        if (patentEntity == null) {
            throw new OwnerException("未查询到专利号为:" + rsPatentPlanEntity.getPatentNo() + "的专利");
        }
        Boolean sign = rsPatentPlanEntity.getPatentNo().equals(model.getNewPatentNo());
        if (!sign) {
            rsPatentPlanEntity.setPatentNo(model.getNewPatentNo());
            rsPatentPlanEntity.setMsLastUpdatorId(userInfo.getId());
            rsPatentPlanEntity.setLastUpdateTime(new Date());
        }
        patentEntity.setPatentNo(model.getNewPatentNo());
        patentEntity.setPatentName(model.getPatentName());
        patentEntity.setApplyDateTime(model.getApplyDateTime());
        patentEntity.setMainType(model.getMainType());
        patentEntity.setInventor(model.getInventor());
        patentEntity.setMsLastUpdatorId(userInfo.getId());
        patentEntity.setLastUpdateTime(new Date());

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!sign) {
                rsPatentPlanDao.updateById(rsPatentPlanEntity);
            }
            rsPatentDao.updateById(patentEntity);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public Boolean update(RsPatentPlanEntity patent) {
        return rsPatentPlanDao.updateById(patent) > 0;
    }

    @Override
    public void judgeAuditPass(List<Integer> patentIds) throws OwnerException {
        if (!CollectionUtils.isEmpty(patentIds)) {
            List<FlowInstancePatent> patents = flowInstancePatentDao.getPassPatents(patentIds);
            if (!CollectionUtils.isEmpty(patents)) {
                PatentPlanEntity entity = patentPlanDao.selectById(patents.get(0).getPatentPlanId());
                throw new OwnerException("申请专利名称：" + entity.getPatentName() + "尚未通过审核，操作失败！");
            }
        }
    }

    @Override
    public Boolean updateFilePath(RsPatentPlanModel model, UserInfo userInfo) throws OwnerException {
        RsPatentPlanEntity patent = rsPatentPlanDao.getPatent(model.getId(), model.getPatentNo());
        if (patent == null) {
            throw new OwnerException("申请专利记录已删除或不存在，操作失败！");
        }
        patent.setFilepath(StringUtils.isEmpty(model.getFilepath()) ? "" : model.getFilepath());
        patent.setLastUpdatorId(-1);
        patent.setLastUpdateTime(new Date());
        patent.setMsLastUpdatorId(userInfo.getId());
        return rsPatentPlanDao.updateById(patent) > 0;
    }

    @Override
    public RsPatentPlanModel getPatentInfo(Integer patentPlanId) {
        RsPatentPlanModel model = rsPatentPlanDao.getPatentInfo(patentPlanId);
        return model;
    }

    @Override
    public RsPatentEntity getByPatentNo(String patentNo) {
        return rsPatentDao.getPlanByPatentNo(patentNo);
    }

    @Override
    public Boolean setName(RsPatentPlanModel model, UserInfo userInfo) throws OwnerException {
        if (null == model || null == model.getId()) {
            throw new OwnerException("数据异常，请联系管理员！");
        }
        RsPatentPlanEntity patentPlan = rsPatentPlanDao.selectById(model.getId());
        if (null == patentPlan) {
            throw new OwnerException("专利申请已删除或不存在，设置失败！");
        }
        patentPlan.setFinalizedName(model.getFinalizedName());
        patentPlan.setLastUpdateTime(new Date());
        patentPlan.setLastUpdatorId(-1);
        patentPlan.setMsLastUpdatorId(userInfo.getId());
        return rsPatentPlanDao.updateById(patentPlan) > 0;
    }

}
