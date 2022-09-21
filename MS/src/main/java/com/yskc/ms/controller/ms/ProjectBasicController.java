package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.project.*;
import com.yskc.ms.service.ms.ProjectBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/31 10:35
 * description:项目基本信息接口
 */

@Api(tags = "项目基本信息接口", value = "项目基本信息接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectBasic")
public class ProjectBasicController extends BaseController{

    @Autowired
    private ProjectBasicService projectBasicService;

    @GetMapping("/getProjectInfo")
    @PermissionRequired(perms = "project:projectList:getBasicInfo")
    @ApiOperation(value = "获取项目信息", notes = "获取项目信息")
    public ProjectBasicModel getProjectInfo(@RequestParam("projectId") Integer projectId) {
        ProjectBasicModel model= projectBasicService.getProjectInfo(projectId);
        if(model!=null){
            return model;
        }
        return new ProjectBasicModel();
    }


    @GetMapping("/getTechInfo")
    @PermissionRequired(perms = "project:projectList:getTechInfo")
    @ApiOperation(value = "获取技术信息", notes = "获取技术信息")
    public ProjectTechInfoModel getTechInfo(@RequestParam("projectId") Integer projectId) {
        return projectBasicService.getTechInfo(projectId);
    }

    @GetMapping("/getFinanceInfo")
    @PermissionRequired(perms = "project:projectList:getFinanceInfo")
    @ApiOperation(value = "获取财务信息", notes = "获取财务信息")
    public ProjectFinanceInfoModel getFinanceInfo(@RequestParam("projectId") Integer projectId){
        return projectBasicService.getFinanceInfo(projectId);
    }

    @PostMapping("/editProjectInfo")
    @PermissionRequired(perms = "project:projectList:editBasicInfo")
    @ApiOperation(value = "修改项目信息", notes = "修改项目信息")
    public Boolean editProjectInfo(@RequestBody @Validated ProjectBasicModel model) throws OwnerException {
        return projectBasicService.editProjectInfo(model,getUserInfo().getId());
    }

    @PostMapping("/editTechInfo")
    @PermissionRequired(perms = "project:projectList:editTechInfo")
    @ApiOperation(value = "修改技术信息", notes = "修改技术信息")
    public Boolean editTechInfo(@RequestBody @Validated ProjectTechInfoModel model) throws OwnerException {
        return projectBasicService.editTechInfo(model,getUserInfo().getId());
    }

    @PostMapping("/editFinanceInfo")
    @PermissionRequired(perms = "project:projectList:editFinanceInfo")
    @ApiOperation(value = "修改财务信息", notes = "修改财务信息")
    public Boolean editFinanceInfo(@RequestBody @Validated ProjectFinanceInfoModel model) throws OwnerException {
        return projectBasicService.editFinanceInfo(model,getUserInfo().getId());
    }
}
