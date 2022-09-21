package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.ProjectDocFileDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.proposal.ProposalAuditEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.enums.ProjectTypeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.achievement.AuditAchievementModel;
import com.yskc.rs.models.finance.AuditFinanceModel;
import com.yskc.rs.models.project.AuditDocFileModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import com.yskc.rs.service.KafkaQueueService;
import com.yskc.rs.service.ProjectAuditService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-21 15:05
 * @Description: 项目审核业务实现层
 */
@Service
public class ProjectAuditServiceImpl implements ProjectAuditService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectAuditDao projectAuditDao;
    @Autowired
    private AuditDocFileDao auditDocFileDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private AuditReportDao auditReportDao;
    @Autowired
    private AuditProposalDao auditProposalDao;
    @Autowired
    private AuditAchievementDao auditAchievementDao;
    @Autowired
    private AuditRdFeeDao auditRdFeeDao;
    @Autowired
    private SummaryDao summaryDao;

    @Override
    public Boolean submitAudit(UserInfo info, ProjectAuditEntity audit) throws OwnerException {
        if (!AuditModeEnum.hasModuleId(audit.getModuleId())) {
            throw new OwnerException("提交审核的模块不存在。");
        }
//        if (audit.getModuleId() == AuditModeEnum.RD_EMPLOYEE.getModuleId() || audit.getModuleId() == AuditModeEnum.RD_EQUIPMENT.getModuleId()) {
//            ProjectAuditEntity projectAuditEntity = projectAuditDao.getAudit(info.getCompanyId(), audit.getYear(), AuditModeEnum.RD_ORG.getModuleId());
//            // 空 未提交状态
//            Integer status = (null == projectAuditEntity ? FlowInstanceStatusEnum.NO_SUBMIT.getStatus() : projectAuditEntity.getStatus());
//            if (FlowInstanceStatusEnum.DONE.getStatus() != status) {
//                throw new OwnerException("组织架构未通过审核，不能提交研发人员名单及研发设备清单审核。");
//            }
//        }
        Integer msUserId = info.getMsUserId();
        audit.setCompanyId(info.getCompanyId());
        // 4 : 提交状态
        audit.setStatus(FlowInstanceStatusEnum.SUBMIT.getStatus());
        Date now = new Date();
        ProjectAuditEntity projectAuditEntity = projectAuditDao.getAudit(info.getCompanyId(), audit.getYear(), audit.getModuleId());
        boolean result;
        if (projectAuditEntity == null) {
            audit.buildCreate(msUserId, now);
            result = projectAuditDao.insert(audit) > 0;
        } else {
            audit.setId(projectAuditEntity.getId());
            audit.buildUpdate(msUserId, now);
            result = projectAuditDao.updateById(audit) > 0;
        }
        kafkaQueueService.submitAudit(CollUtil.newArrayList(audit.getId()), msUserId, FlowModuleTypeEnum.RD_TECH);
        return result;
    }

    @Override
    public Integer getAudit(UserInfo userInfo, Integer year, Integer moduleId) throws OwnerException {
        AuditModeEnum modeEnum = AuditModeEnum.getByModuleId(moduleId);
        if (null == modeEnum) {
            return FlowInstanceStatusEnum.NO_SUBMIT.getStatus();
        }
        ProjectAuditEntity audit = projectAuditDao.getAudit(userInfo.getCompanyId(), year, modeEnum.getModuleId());
        // 空 未提交状态
        return null == audit ? FlowInstanceStatusEnum.NO_SUBMIT.getStatus() : audit.getStatus();
    }

    @Override
    public Boolean submitDocAudit(UserInfo info, AuditDocFileModel audit) throws OwnerException {
        if (CollectionUtils.isEmpty(audit.getDocFileId())) {
            return true;
        }
        List<ProjectDocFileEntity> docFiles = projectDocFileDao.verifyDoc(audit.getDocFileId(), audit.getProjectId());
        if (CollectionUtils.isEmpty(docFiles)) {
            throw new OwnerException("提交审核的文件不存在或与项目不符，请联系管理员");
        }
        Integer status = auditReportDao.getProjectStatus(audit.getProjectId());
        if (Objects.equals(status, null) || status != FlowInstanceStatusEnum.DONE.getStatus()) {
            throw new OwnerException("项目名称未通过审核，不能提交过程文档审核。");
        }

        //将空文档验证改为finished验证
        for (ProjectDocFileEntity entity : docFiles) {
            if (!entity.getFinished()) {
                throw new OwnerException(MessageFormat.format("阶段：{0}的过程文件：{1}，未编写完整，请编写必填项后再提交！", entity.getStage(), entity.getDocFileName()));
            }
        }
        /*List<SubmitFileModel> submitFileModels = projectDocFileDao.verifySubmitFile(audit.getDocFileId());
            if (!CollectionUtils.isEmpty(submitFileModels)) {
            for (SubmitFileModel model : submitFileModels) {
                if (model.getNeedEdit() != null && model.getNeedEdit()) {
                    if (model.getDocFileDataId() == null) {
                        throw new OwnerException(MessageFormat.format("阶段：{0}，过程文件：{1}需要编辑后方可提交！", model.getStageName(), model.getDocFileName()));
                    }
                }
            }
        }*/
        Map<Integer, Integer> docModuleMap = new HashMap<>();
        for (ProjectDocFileEntity entity : docFiles) {
            if (entity.getDocFileId().equals(27)) {
                docModuleMap.put(entity.getId(), AuditModeEnum.RD_REPORT.getModuleId());
            } else if (entity.getDocFileId().equals(38)) {
                docModuleMap.put(entity.getId(), AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId());
            } else {
                docModuleMap.put(entity.getId(), AuditModeEnum.RD_DOC.getModuleId());
            }
        }
        List<AuditDocFileEntity> inserts = new ArrayList<>();
        Date date = new Date();
        List<Integer> hasAuditIds = new ArrayList<>();
        List<AuditDocFileEntity> auditDocFiles = auditDocFileDao.getDocAuditStatus(audit.getProjectId(), audit.getDocFileId());
        for (AuditDocFileEntity auditDocFile : auditDocFiles) {
            if (!FlowInstanceStatusEnum.canModify(auditDocFile.getStatus())) {
                throw new OwnerException("已提交审核，不能重复提交！");
            }
            hasAuditIds.add(auditDocFile.getDocFileId());
        }
        Integer msUserId = info.getMsUserId();
        List<Integer> insertIds = new ArrayList<>(CollUtil.disjunction(hasAuditIds, audit.getDocFileId()));
        if (!CollectionUtils.isEmpty(insertIds)) {
            for (Integer docFileId : insertIds) {
                AuditDocFileEntity addFile = new AuditDocFileEntity(date, msUserId, info.getCompanyId(), audit.getProjectId(), docFileId, docModuleMap.get(docFileId), 4);
                inserts.add(addFile);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(inserts)) {
                auditDocFileDao.batchAdd(inserts);
            }
            if (!CollectionUtils.isEmpty(hasAuditIds)) {
                auditDocFileDao.batchUpdate(hasAuditIds, date, msUserId, 4);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("submitDocAudit", ex);
        }
        kafkaQueueService.submitAudit(audit.getDocFileId(), msUserId, FlowModuleTypeEnum.RD_DOC_FILE);
        return true;
    }

    @Override
    public Map<Integer, Integer> countDocAuditData(UserInfo userInfo, Integer projectId) throws OwnerException {
        Map<Integer, Integer> dataMap = new HashMap<>();
        List<ProjectDocFileModel> docFiles = auditDocFileDao.countAuditData(projectId);
        for (ProjectDocFileModel model : docFiles) {
            Integer status = model.getStatus();
            if (FlowInstanceStatusEnum.isSubmit(status)) {
                status = FlowInstanceStatusEnum.ONGOING.getStatus();
            }
            dataMap.put(status, dataMap.getOrDefault(status, 0) + 1);
        }
        ProjectTypeEnum[] values = ProjectTypeEnum.values();
        Map<Integer, String> map = new HashMap<>();
        for (ProjectTypeEnum value : values) {
            map.put(value.getType(), value.getTypeName());
        }
//        ProjectStateEntity projectState = projectStateDao.getByProject(projectId);
//        dataMap.put(6, projectState != null ? projectState.getStatus() : 0);
        return dataMap;
    }

    @Override
    public Boolean submitProjectAudit(UserInfo info, ProjectAuditEntity audit) throws OwnerException {
        if (!AuditModeEnum.hasModuleId(audit.getModuleId())) {
            throw new OwnerException("提交审核的模块不存在。");
        }
        if (CollectionUtils.isEmpty(audit.getProjectIds())) {
            throw new OwnerException("请选择要提交审核的项目！");
        }
        audit.setCompanyId(info.getCompanyId());
        // 4 : 提交状态
        audit.setStatus(4);
        Date now = new Date();
        List<AuditReportEntity> projectAudits = auditReportDao.getProjectAudits(audit.getProjectIds(), 4);
        Map<Integer, AuditReportEntity> projectMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(projectAudits)) {
            for (AuditReportEntity entity : projectAudits) {
                if (!FlowInstanceStatusEnum.canModify(entity.getStatus())) {
                    throw new OwnerException("已提交审核的项目不能重复提交！");
                }
                projectMap.put(entity.getProjectId(), entity);
            }

        }
        Integer msUserId = info.getMsUserId();
        //ProjectAuditEntity projectAuditEntity = projectAuditDao.getAudit(info.getCompanyId(), audit.getYear(), audit.getModuleId());
        List<AuditReportEntity> audits = new ArrayList<>();
        for (Integer projectId : audit.getProjectIds()) {
            audits.add(new AuditReportEntity(now, msUserId, info.getCompanyId(), projectId));
        }
        if (!CollectionUtils.isEmpty(audits)) {
            auditReportDao.insertOrUpdate(audits);
            List<Integer> projectIds = audits.stream().map(e -> e.getProjectId()).collect(Collectors.toList());
            kafkaQueueService.submitAudit(projectIds, msUserId, FlowModuleTypeEnum.RD_PROJECT);
        }
        return true;
    }

    @Override
    public Boolean submitProposalAudit(UserInfo info, ProposalAuditEntity audit) throws OwnerException {
        if (!AuditModeEnum.hasModuleId(audit.getModuleId())) {
            throw new OwnerException("提交审核的模块不存在。");
        }
        if (CollectionUtils.isEmpty(audit.getProposalIds())) {
            throw new OwnerException("请选择要提交审核的提案！");
        }
        audit.setCompanyId(info.getCompanyId());
        // 4 : 提交状态
        audit.setStatus(FlowInstanceStatusEnum.SUBMIT.getStatus());
        Date now = new Date();
        List<ProposalAuditEntity> proposalAudits = auditProposalDao.getProposalAudits(audit.getProposalIds(), 11);
        Map<Integer, ProposalAuditEntity> proposalMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(proposalAudits)) {
            for (ProposalAuditEntity entity : proposalAudits) {
                if (!FlowInstanceStatusEnum.canModify(entity.getStatus())) {
                    throw new OwnerException("已提交审核的提案不能重复提交！");
                }
                proposalMap.put(entity.getProposalId(), entity);
            }
        }
        Integer msUserId = info.getMsUserId();
        List<ProposalAuditEntity> audits = new ArrayList<>();
        for (Integer proposalId : audit.getProposalIds()) {
            audits.add(new ProposalAuditEntity(now, msUserId, info.getCompanyId(), proposalId));
        }
        if (!CollectionUtils.isEmpty(audits)) {
            auditProposalDao.insertOrUpdate(audits);
            List<Integer> proposalIds = audits.stream().map(e -> e.getProposalId()).collect(Collectors.toList());
            kafkaQueueService.submitAudit(proposalIds, msUserId,FlowModuleTypeEnum.RD_PROPOSAL);
        }
        return true;
    }

    @Override
    public Boolean submitAchievement(UserInfo info, AuditAchievementModel audit) throws OwnerException {
        Integer moduleId = audit.getModuleId();
        if (!AuditModeEnum.hasModuleId(moduleId)) {
            throw new OwnerException("提交审核的模块不存在。");
        }
        if (CollectionUtils.isEmpty(audit.getAchievementId())) {
            throw new OwnerException("请选择要提交审核的成果！");
        }
        List<AuditAchievementEntity> audits = new ArrayList<>();
        Date now = new Date();
        Integer msUserId = info.getMsUserId(),status = FlowInstanceStatusEnum.SUBMIT.getStatus();
        for (Integer documentId : audit.getAchievementId()) {
            audits.add(new AuditAchievementEntity(now, moduleId, msUserId, info.getCompanyId(), documentId, status));
        }
        if (!CollectionUtils.isEmpty(audits)) {
            auditAchievementDao.insertOrUpdate(audits);
            kafkaQueueService.submitAudit(audit.getAchievementId(),msUserId, FlowModuleTypeEnum.RD_ACHIEVEMENT);
        }
        return true;
    }

    @Override
    public Boolean submitRdFeeAudit(UserInfo info, AuditFinanceModel model) throws OwnerException {
        BigDecimal rdFunds = summaryDao.getRdFunds(model, CostEnum.getBelongRdTypes(model.getRdType()));
        if (null == rdFunds || rdFunds.compareTo(BigDecimal.ZERO) == 0) {
            throw new OwnerException("尚未归集费用，请归集费用后再提交！");
        }
        model.setCompanyId(info.getCompanyId());
        AuditRdFeeEntity entity = auditRdFeeDao.getAudit(model);
        Boolean exist = null != entity;
        if (exist && !FlowInstanceStatusEnum.canModify(entity.getStatus())) {
            throw new OwnerException("该条记录已提交，请勿重复操作！");
        }
        Date now = new Date();
        Integer status = FlowInstanceStatusEnum.SUBMIT.getStatus(), msUserId = info.getMsUserId();
        if (exist) {
            auditRdFeeDao.updateStatus(entity.getId(), status, msUserId, now);
        } else {
            entity = new AuditRdFeeEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setModuleId(AuditModeEnum.RD_FEE.getModuleId());
            entity.setStatus(status);
            entity.setMsCreatorId(msUserId);
            entity.setMsLastUpdatorId(msUserId);
            entity.setCreateTime(now);
            entity.setLastUpdateTime(now);
            auditRdFeeDao.insert(entity);
        }
        kafkaQueueService.submitAudit(Arrays.asList(entity.getId()),msUserId, FlowModuleTypeEnum.RD_FEE);
        return true;
    }
}
