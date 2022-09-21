package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.PatentOpinionDao;
import com.yskc.rs.dao.PatentPlanDao;
import com.yskc.rs.dao.project.AuditPatentDao;
import com.yskc.rs.dao.project.PatentFileDao;
import com.yskc.rs.entity.PatentOpinionEntity;
import com.yskc.rs.entity.PatentPlanEntity;
import com.yskc.rs.entity.project.AuditPatentEntity;
import com.yskc.rs.enums.PatentApplyEnum;
import com.yskc.rs.models.PatentPlan.AddPatentPlanModel;
import com.yskc.rs.models.PatentPlan.PatentOpinionModel;
import com.yskc.rs.models.PatentPlan.PatentPlanModel;
import com.yskc.rs.models.PatentPlan.QueryPatentPlanModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.KafkaQueueService;
import com.yskc.rs.service.PatentPlanService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/6 10:48
 * description:
 */
@Service
public class PatentPlanServiceImpl implements PatentPlanService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuditPatentDao auditPatentDao;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private PatentOpinionDao patentOpinionDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private PatentFileDao patentFileDao;

    @Override
    public PageModel<List<PatentPlanModel>> queryPatentPlan(QueryPatentPlanModel model, UserInfo userInfo) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pp.createTime");
            page.setDescs(descs);
        }
        if (!StringUtils.isEmpty(model.getStatusName())) {
            model.setStatus(PatentApplyEnum.getType(model.getStatusName()));
        }
        List<PatentPlanModel> patentPlanModels = patentPlanDao.queryPatentPlans(page, model, userInfo.getCompanyId());
        if (!CollectionUtils.isEmpty(patentPlanModels)) {
            for (PatentPlanModel planModel : patentPlanModels) {
                planModel.setRD(planModel.getRdTitle());
                String path = planModel.getDisclosureParperPath();
                String docName = "";
                if(!StringUtils.isEmpty(path)){
                    docName=path.substring(path.lastIndexOf("/")+14);
                }
                planModel.setDocName(docName);
            }
        }
        return PageUtils.build(page, patentPlanModels);
    }

    @Override
    public Boolean addPatentPlan(AddPatentPlanModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        PatentPlanEntity planEntity = new PatentPlanEntity();
        planEntity.setCompanyId(userInfo.getCompanyId());
        planEntity.setCreateTime(date);
        planEntity.setCreatorId(userInfo.getUserId());
        planEntity.setLastUpdateTime(date);
        planEntity.setLastUpdatorId(userInfo.getUserId());
        planEntity.setMsCreatorId(userInfo.getMsUserId());
        planEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        planEntity.setProjectId(model.getProjectId());
        planEntity.setDescription(model.getDescription());
        planEntity.setDisclosureParperPath(model.getFilePath());
        planEntity.setStatus(0);
        planEntity.setPatentName(model.getPatentName());
        planEntity.setSource(0);
        planEntity.setInventor(model.getInventor());
        planEntity.setInventorInfo(model.getInventorInfo());
        planEntity.setYear(model.getYear());
        return patentPlanDao.insert(planEntity) > 0;
    }

    @Override
    public Boolean delPatentPlan(PatentPlanModel model, UserInfo userInfo) throws OwnerException {
        PatentPlanEntity planEntity = patentPlanDao.selectById(model.getId());
        if (planEntity.getStatus() == 0 || planEntity.getStatus() == 3) {
            return patentPlanDao.deleteById(model.getId()) > 0;
        }
        throw new OwnerException("该专利在申请中或已完成申请，无法删除");
    }

    @Override
    public Boolean editPatentPlan(AddPatentPlanModel model, UserInfo userInfo) throws OwnerException {
        PatentPlanEntity planEntity = patentPlanDao.selectById(model.getId());
        if (planEntity != null) {
            if (userInfo.getUserSource() > 0) {
                List<PatentPlanModel> auditPatents = auditPatentDao.getPatentStatus(Arrays.asList(planEntity.getId()));
                if (CollectionUtils.isEmpty(auditPatents)) {
                    return true;
                }
                PatentPlanModel planModel = auditPatents.get(0);
                if (!FlowInstanceStatusEnum.canModify(planModel.getAuditStatus())) {
                    throw new OwnerException("已提交审核，不能编辑！");
                }
            }
            if (planEntity.getStatus() == 3) {
                planEntity.setStatus(0);
                planEntity.setMasterId(-1);
            }
            planEntity.setPatentName(model.getPatentName());
            planEntity.setDisclosureParperPath(model.getFilePath());
            planEntity.setDescription(model.getDescription());
            planEntity.setInventor(model.getInventor());
            planEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            planEntity.setLastUpdateTime(new Date());
            planEntity.setLastUpdatorId(userInfo.getUserId());
            planEntity.setInventorInfo(model.getInventorInfo());
            return patentPlanDao.updateById(planEntity) > 0;

        }
        throw new OwnerException("数据已删除或不存在，编辑失败");
    }

    @Override
    public PatentPlanEntity getFilePath(Integer id) {
        PatentPlanEntity planEntity = patentPlanDao.selectById(id);
        return planEntity;
    }

    @Override
    public Boolean submitPatents(UserInfo info, List<Integer> patentIds) throws OwnerException {
        if (CollectionUtils.isEmpty(patentIds)) {
            throw new OwnerException("请选择要提交的申请专利后再提交！");
        }
        Date date = new Date();
        Integer msUserId = info.getMsUserId();
        List<PatentPlanModel> patents = auditPatentDao.getPatentStatus(patentIds);
        List<AuditPatentEntity> inserts = new ArrayList<>();
        List<Integer> updatePatents = new ArrayList<>();
        if (!CollectionUtils.isEmpty(patents)) {
            for (PatentPlanModel patentPlan : patents) {
                if (patentPlan.getAuditStatus() != null) {
                    if (!FlowInstanceStatusEnum.canModify(patentPlan.getAuditStatus())) {
                        throw new OwnerException(patentPlan.getPatentName() + "已提交审核,不能再次提交！");
                    }
                    updatePatents.add(patentPlan.getId());
                } else {
                    AuditPatentEntity entity = new AuditPatentEntity(info.getCompanyId(), patentPlan.getId(), msUserId, date);
                    inserts.add(entity);
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(inserts)) {
                auditPatentDao.batchAdd(inserts);
            }
            if (!CollectionUtils.isEmpty(updatePatents)) {
                auditPatentDao.batchUpdate(updatePatents, date, msUserId, 4);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("submitPatents", ex);
        }
        kafkaQueueService.submitAudit(patentIds, msUserId,FlowModuleTypeEnum.RD_PATENT);
        return true;
    }

    @Override
    public List<PatentOpinionModel> getPatentOpinions(Integer patentPlanId,String patentNo) throws OwnerException {
        PatentPlanEntity patentPlan;
        if(patentPlanId!=null){
            patentPlan=patentPlanDao.getPatent(patentPlanId,null);
        }else {
            patentPlan=patentPlanDao.getPatent(null,patentNo);
        }
        if(null==patentPlan){
            return new ArrayList<>();
        }
        check(patentPlan.getId());
        PatentOpinionModel model = new PatentOpinionModel();
        model.setPatentPlanId(patentPlan.getId());
        model.setFilepath(patentPlan.getFilepath());
        model.setCreateTime(patentPlan.getCreateTime());
        List<PatentOpinionModel> models = patentOpinionDao.getOpinions(patentPlan.getId());
        models.add(0, model);
        return models;
    }

    @Override
    public Boolean saveOpinion(PatentOpinionModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            return true;
        }
        Date date = new Date();
         check(model.getPatentPlanId());
        if (model.getId() != null) {
            PatentOpinionEntity opinion = patentOpinionDao.selectById(model.getId());
            if (opinion == null) {
                throw new OwnerException("该条意见已删除或不存在！");
            }
            BeanUtils.copyProperties(model, opinion);
            opinion.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            return patentOpinionDao.updateById(opinion) > 0;
        } else {
            PatentOpinionEntity opinionEntity = new PatentOpinionEntity();
            BeanUtils.copyProperties(model, opinionEntity);
            opinionEntity.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            opinionEntity.setCompanyId(userInfo.getCompanyId());
            return patentOpinionDao.insert(opinionEntity) > 0;
        }
    }

    @Override
    public Boolean delOpinions(List<Integer> opinionIds) {
        if (CollectionUtils.isEmpty(opinionIds)) {
            return true;
        }
        return patentOpinionDao.deleteBatchIds(opinionIds) > 0;
    }

    @Override
    public PatentPlanEntity getPatent(Integer id, String patentNo) {
        PatentPlanEntity patent=patentPlanDao.getPatent(id,patentNo);
        return patent;
    }


    private void check(Integer patentPlanId) throws OwnerException {
        AuditPatentEntity auditPatent = auditPatentDao.getByPatent(patentPlanId);
        if (auditPatent == null || auditPatent.getStatus() != 1) {
            throw new OwnerException("需完成审核后才能操作！");
        }
    }
}
