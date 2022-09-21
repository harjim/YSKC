package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.ProjectNumberEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.ProjectModel;
import com.yskc.rs.models.project.TypeModel;
import com.yskc.rs.service.ProjectNumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "项目个数类接口", value = "项目个数类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectNumber")
public class ProjectNumberController extends BaseController {

    @Autowired
    private ProjectNumberService projectNumberService;

    @SystemLog(description = "设置项目数", mode = SystemLog.SAVE_DB)
    @PostMapping("/setProjectNum")
    @PermissionRequired(perms = "project:report:number")
    @ApiOperation(value = "设置项目数", notes = "设置项目数")
    public Boolean setProjectNum(@RequestBody @Validated ProjectNumberEntity entity) throws OwnerException {
        return projectNumberService.setProjectNum(entity, getUserInfo());
    }

    /**
     * @param year
     * @return
     */
    @GetMapping("/queryProjectNum")
    @ApiOperation(value = "获取项目数", notes = "获取项目数")
    public Integer queryProjectNum(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectNumberService.queryProjectNum(userInfo.getCompanyId(),year);
    }
}
