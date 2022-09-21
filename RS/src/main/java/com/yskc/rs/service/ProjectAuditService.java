package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.proposal.ProposalAuditEntity;
import com.yskc.rs.entity.project.ProjectAuditEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.achievement.AuditAchievementModel;
import com.yskc.rs.models.finance.AuditFinanceModel;
import com.yskc.rs.models.project.AuditDocFileModel;

import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-21 15:05
 * @Description: 项目审核业务接口层
 */
public interface ProjectAuditService {
    /**
     * 提交项目审核
     *
     * @param info
     * @param audit
     * @return
     * @throws OwnerException
     */
    Boolean submitAudit(UserInfo info, ProjectAuditEntity audit) throws OwnerException;

    /**
     * 获取审核状态
     *
     * @param userInfo
     * @param year
     * @param moduleId
     * @return
     * @throws OwnerException
     */
    Integer getAudit(UserInfo userInfo, Integer year, Integer moduleId) throws OwnerException;

    /**
     * 提交项目过程文件审核
     *
     * @param info
     * @param audit
     * @return
     */
    Boolean submitDocAudit(UserInfo info, AuditDocFileModel audit) throws OwnerException;

    /**
     * 获取过程文件审核统计
     *
     * @param userInfo
     * @param projectId
     * @return
     * @throws OwnerException
     */
    Map<Integer, Integer> countDocAuditData(UserInfo userInfo, Integer projectId) throws OwnerException;


    /**
     * 项目提交审核
     *
     * @param info
     * @param audit
     * @return
     */
    Boolean submitProjectAudit(UserInfo info, ProjectAuditEntity audit) throws OwnerException;

    /**
     * 提案提交审核
     *
     * @param info
     * @param audit
     * @return
     */
    Boolean submitProposalAudit(UserInfo info, ProposalAuditEntity audit) throws OwnerException;

    /**
     * 成果提交审核
     *
     * @param info
     * @param audit
     * @return
     * @throws OwnerException
     */
    Boolean submitAchievement(UserInfo info, AuditAchievementModel audit) throws OwnerException;

    /**
     * 提交财务审核
     * @param info
     * @param model
     * @return
     */
    Boolean submitRdFeeAudit(UserInfo info, AuditFinanceModel model) throws OwnerException;
}
