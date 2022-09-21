package com.yskc.rs.controller;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.buildconfig.BuildConfigModel;
import com.yskc.rs.service.BuildConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: rs
 * @description: 机构建设事项事项配置Controller
 * @author: cyj
 * @create: 2022-01-05 08:50
 **/
@Api(tags = "机构建设事项事项配置接口", value = "机构建设事项事项配置接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/buildConfig")
public class BuildConfigController extends BaseController{
    @Autowired
    private BuildConfigService buildConfigService;

    @SystemLog(description = "编辑/引入事项配置", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "rdorg:buildList:setting")
    @PostMapping("/save")
    @ApiOperation(value = "编辑/引入事项配置", notes = "编辑/引入事项配置")
    public boolean saveBuildConfig(@RequestBody BuildConfigModel[] models) throws OwnerException{
        return buildConfigService.saveBuildConfig(models,getUserInfo());
    }

    @GetMapping("/getList")
    @ApiOperation(value = "按公司和年份获取事项配置", notes = "按公司和年份获取事项配置")
    public List<BuildConfigModel> getList(Integer year) throws OwnerException {
        return buildConfigService.getBuildConfig(getUserInfo().getCompanyId(),year);
    }
}
