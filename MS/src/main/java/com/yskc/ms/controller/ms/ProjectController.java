package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.params.ProjectParams;
import com.yskc.ms.models.project.BatchStaffModel;
import com.yskc.ms.models.project.ProjectInfoModel;
import com.yskc.ms.models.project.SetOwerModel;
import com.yskc.ms.service.ms.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "项目类接口", value = "项目类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    @PermissionRequired(perms = "project:projectList:search")
    @ApiOperation(value = "获取项目列表", notes = "获取项目列表")
    public PageModel<List<ProjectInfoModel>> getList(ProjectParams query) throws OwnerException {
        return projectService.getProjectList(query, getUserInfo(), getDataPerm());
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取项目详情", notes = "获取项目详情")
    public ProjectInfoModel getDetail(Integer projectId) throws OwnerException {
        return projectService.getProjectDetail(getUserInfo(), projectId);
    }

    @PostMapping("/update")
    @ApiOperation(notes = "更新项目", value = "更新项目或技改详情")
    @SystemLog(description = "更新项目", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:projectList:edit,project:projectList:tech,project:projectList:fina")
    public boolean update(@RequestBody @Validated ProjectInfoModel projectInfo) throws OwnerException {
        return projectService.updateProject(getUserInfo(), projectInfo);
    }

    @PostMapping("/add")
    @ApiOperation(notes = "添加项目", value = "添加项目或技改详情")
    @SystemLog(description = "添加项目", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:projectList:add")
    public boolean add(@RequestBody @Validated ProjectInfoModel projectInfo) throws OwnerException {
        return projectService.addProject(getUserInfo(), projectInfo);
    }

    @PostMapping("/setProjectTechIds")
    @PermissionRequired(perms = "project:projectList:tech")
    @ApiOperation(value = "批量设置技术人员", notes = "批量设置技术人员")
    @SystemLog(description = "批量设置技术人员", mode = SystemLog.SAVE_DB)
    public boolean setProjectTechIds(@Validated @RequestBody BatchStaffModel batch) throws OwnerException {
        return projectService.setProjectTechIds(batch, getUserInfo(),batch.getAdd());
    }

    @PostMapping("/setProjectFinanceIds")
    @PermissionRequired(perms = "project:projectList:fina")
    @ApiOperation(value = "批量设置财务人员", notes = "批量设置财务人员")
    @SystemLog(description = "批量设置财务人员", mode = SystemLog.SAVE_DB)
    public boolean setProjectFinanceIds(@Validated @RequestBody BatchStaffModel batch) throws OwnerException {
        return projectService.setProjectFinanceIds(batch, getUserInfo(),batch.getAdd());
    }

    @PostMapping("/setProjectOwerIds")
    @PermissionRequired(perms = "project:projectList:ower")
    @ApiOperation(value = "批量设置业务人员", notes = "批量设置业务人员")
    @SystemLog(description = "批量设置业务人员", mode = SystemLog.SAVE_DB)
    public boolean setProjectOwerIds(@Validated @RequestBody SetOwerModel setOwer) throws OwnerException {
        return projectService.setProjectOwerIds(setOwer, getUserInfo());
    }
}
