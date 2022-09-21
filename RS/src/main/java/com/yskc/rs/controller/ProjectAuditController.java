package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectAuditEntity;
import com.yskc.rs.entity.proposal.ProposalAuditEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.achievement.AuditAchievementModel;
import com.yskc.rs.models.finance.AuditFinanceModel;
import com.yskc.rs.models.project.AuditDocFileModel;
import com.yskc.rs.service.ProjectAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-21 15:03
 * @Description: 项目审核接口层
 */
@Api(value = "项目审核接口层", tags = "项目审核接口层")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectAudit")
public class ProjectAuditController extends BaseController {

    @Autowired
    private ProjectAuditService projectAuditService;

    @PostMapping("/submitAudit")
    @ApiOperation(value = "提交审核", notes = "提交审核")
    @SystemLog(description = "提交审核", mode = SystemLog.SAVE_DB)
    public Boolean submitAudit(@RequestBody ProjectAuditEntity audit) throws OwnerException {
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitAudit(info, audit);
    }

    @PostMapping("/submitProjectAudit")
    @ApiOperation(value = "提交项目审核", notes = "提交项目审核")
    @SystemLog(description = "提交项目审核", mode = SystemLog.SAVE_DB)
    public Boolean submitProjectAudit(@RequestBody ProjectAuditEntity audit) throws OwnerException {
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitProjectAudit(info, audit);
    }

    @GetMapping("/getAudit")
    @ApiOperation(value = "获取项目审核状态", notes = "获取项目审核状态")
    public Integer getAudit(Integer year,Integer moduleId)throws OwnerException{
        return projectAuditService.getAudit(getUserInfo(),year,moduleId);
    }

    @PostMapping("/submitDocAudit")
    @ApiOperation(value = "提交项目过程文件审核", notes = "提交项目过程文件审核")
    @SystemLog(description = "提交项目过程文件审核", mode = SystemLog.SAVE_DB)
    public Boolean submitDocAudit(@RequestBody AuditDocFileModel audit) throws OwnerException {
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitDocAudit(info, audit);
    }
//查新报告按过程文件提交
//    @PostMapping("/submitReportAudit")
//    @ApiOperation(value = "提交项目查新报告审核", notes = "提交项目查新报告审核")
//    @SystemLog(description = "提交项目查新报告审核", mode = SystemLog.SAVE_DB)
//    public Boolean submitReportAudit(@RequestBody AuditDocFileModel audit) throws OwnerException {
//        UserInfo info = getUserInfo();
//        if (info.getMsUserId() <= 0) {
//            throw new OwnerException("管理员身份验证失败，请稍后重试。");
//        }
//        return projectAuditService.submitReportAudit(info, audit);
//    }

    /**
     * 获取项目过程文件审核统计数据
     * @param projectId
     * @return map key： 0-5文件审核状态 6：项目是否结项
     * @throws OwnerException
     */
    @GetMapping("/countDocAuditData")
    @ApiOperation(value = "获取项目过程文件审核统计数据", notes = "获取项目过程文件审核统计数据")
    public Map<Integer,Integer> countDocAuditData(Integer projectId)throws OwnerException{
        return projectAuditService.countDocAuditData(getUserInfo(),projectId);
    }

    @PostMapping("/submitProposal")
    @ApiOperation(value = "提交提案审核", notes = "提交提案审核")
    @SystemLog(description = "提交提案审核", mode = SystemLog.SAVE_DB)
    public Boolean submitProposal(@RequestBody ProposalAuditEntity proposalAuditEntry) throws OwnerException{
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitProposalAudit(info,proposalAuditEntry);
    }

    @PostMapping("/submitAchievement")
    @ApiOperation(value = "提交成果审核", notes = "提交成果审核")
    @SystemLog(description = "提交成果审核", mode = SystemLog.SAVE_DB)
    public Boolean submitAchievement(@RequestBody AuditAchievementModel audit) throws OwnerException{
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitAchievement(info,audit);
    }

    @PostMapping("/submitRdFeeAudit")
    @PermissionRequired(perms="project:data:submit")
    @ApiOperation(value = "提交归集费用审核", notes = "提交归集费用审核")
    @SystemLog(description = "提交归集费用审核", mode = SystemLog.SAVE_DB)
    public Boolean submitRdFeeAudit(@RequestBody AuditFinanceModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return projectAuditService.submitRdFeeAudit(info, model);
    }
}
