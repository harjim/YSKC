package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.SenseModel;
import com.yskc.rs.service.SenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/10/12 16:35
 * @Description:
 * @author: hsx
 */
@Api(tags = "研发意识管理管理接口", value = "研发意识管理管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/senseManagement")
public class SenseController extends BaseController{

    @Autowired
    private SenseService senseService;

    @SystemLog(description = "新增研发意识管理阶段记录")
    @PostMapping("/addSense")
    @PermissionRequired(perms = "project:sense:add")
    @ApiOperation(value = "新增研发意识管理阶段记录", notes = "新增研发意识管理阶段记录", response = boolean.class)
    public boolean addSense(@RequestBody @Validated SenseModel senseModel) throws OwnerException {
        return senseService.addSense(senseModel,getUserInfo());
    }

    @GetMapping("/getSenses")
    @PermissionRequired(perms = "project:sense:search")
    @ApiOperation(value = "获取研发意识管理阶段记录", notes = "获取研发意识管理阶段记录", response = Map.class)
    public Map<Integer, List<SenseModel>> getSenses() throws OwnerException {
        return senseService.getSenses(getUserInfo().getCompanyId());
    }

    @SystemLog(description = "删除研发意识管理阶段记录")
    @PostMapping("/delSense")
    @PermissionRequired(perms = "project:sense:del")
    @ApiOperation(value = "删除研发意识管理阶段记录", notes = "删除研发意识管理阶段记录", response = boolean.class)
    public boolean delSense(@RequestBody List<Integer> ids) {
        return senseService.delSense(ids);
    }

    @SystemLog(description = "编辑研发意识管理阶段记录")
    @PostMapping("/editSense")
    @PermissionRequired(perms = "project:sense:edit")
    @ApiOperation(value = "编辑研发意识管理阶段记录", notes = "编辑研发意识管理阶段记录", response = boolean.class)
    public boolean editSense(@RequestBody @Validated SenseModel model) throws OwnerException {
        return senseService.editSense(model,getUserInfo());
    }
}
