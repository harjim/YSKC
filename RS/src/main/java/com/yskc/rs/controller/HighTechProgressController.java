package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.enums.HighTechProgressNodeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.*;
import com.yskc.rs.models.hightechprogress.*;
import com.yskc.rs.models.hightechprogress.HighTechFileModel;
import com.yskc.rs.models.project.DeliverLogModel;
import com.yskc.rs.service.HighTechProgressService;
import com.yskc.rs.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: rs
 * @description: 高新进度
 * @author: wjy
 * @create: 2022-05-19 09:02
 **/
@Api(tags = "高新进度接口", value = "高新进度接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/highTechProgress")
public class HighTechProgressController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HighTechProgressService highTechProgressService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "deliver:detail:search")
    @ApiOperation(value = "获取高新进度列表", notes = "获取高新进度列表")
    public PageModel<List<HighTechProgressModel>> getList(QueryHighTechProgModel query) throws OwnerException {
        List<Integer> companyIds = getUserInfo().getCompanyIds();
        PageModel<List<HighTechProgressModel>> list = highTechProgressService.getList(companyIds, query);
        return list;
    }

    @GetMapping("/getLogList")
    @PermissionRequired(perms = "deliver:detail:search")
    @ApiOperation(value = "获取高新进度日志列表", notes = "获取高新进度日志列表")
    public List<DeliverLogModel> getLogList(QueryHighTechAuditModel query) throws OwnerException {

        return highTechProgressService.getLogList(query);
    }

    @GetMapping("/getAssistData")
    @PermissionRequired(perms = "deliver:detail:finaAudit")
    @ApiOperation(value = "获取项目辅助账", notes = "获取项目辅助账")
    public Map<String, Object> getAssistData(QueryAssistModel query) throws OwnerException {
        return projectService.getAssistData(query);
    }

    @GetMapping("/getTotalList")
    @PermissionRequired(perms = "deliver:detail:search")
    @ApiOperation(value = "获取项目统计明细", notes = "获取项目统计明细")
    public List<HighTechTotalModel> getTotalList(Integer year) throws OwnerException {
        List<Integer> companyIds = getUserInfo().getCompanyIds();
        return highTechProgressService.getTotalList(companyIds,year);
    }

    @PostMapping("/submitAudit")
    @PermissionRequired(perms = "deliver:detail:techAudit,deliver:detail:finaAudit")
    @ApiOperation(value = "审核高新进度列表", notes = "审核高新进度列表")
    @SystemLog(description = "提交高新进度审核", mode = SystemLog.SAVE_DB)
    public Boolean submitAudit(@RequestBody @Validated QueryHighTechAuditModel query) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        if (!HighTechProgressNodeEnum.hasNode(query.getNode(), userInfo.getRoleType(), userInfo.getUserSource())) {
            throw new OwnerException("审核失败");
        }
        return highTechProgressService.submitAudit(query, userInfo);
    }

    @PostMapping("/allot")
    @PermissionRequired(perms = "deliver:detail:allot")
    @SystemLog(description = "分配人员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "分配人员", notes = "分配人员")
    public Boolean allot(@RequestBody HighTechAllotModel model) throws OwnerException {
        return highTechProgressService.allot(model, getUserInfo());
    }

    @GetMapping("/getDetailList")
    @PermissionRequired(perms = "deliver:statistics:techView,deliver:statistics:finaView")
    @ApiOperation(value = "公司分类查询高新进度明细", notes = "公司分类查询高新进度明细")
    public Map<String,List<HighTechProjectModel>> getDetailList(QueryHighTechFileModel model) throws OwnerException {
        return highTechProgressService.getProjectList(model, getUserInfo());
    }

    @PostMapping("/submitsAudit")
    @PermissionRequired(perms = "deliver:statistics:techAudit,deliver:statistics:finaAudit")
    @ApiOperation(value = "高新进度技术文档批量审核", notes = "高新进度技术文档批量审核")
    @SystemLog(description = "高新进度技术文档批量审核", mode = SystemLog.SAVE_DB)
    public Boolean techsAudit(@RequestBody @Validated TechAuditBatchModel model) throws OwnerException {
        return highTechProgressService.submitsAudit(model, getUserInfo());
    }
}
