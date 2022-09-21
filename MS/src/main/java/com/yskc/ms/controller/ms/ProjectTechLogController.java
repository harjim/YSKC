package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.tech.*;
import com.yskc.ms.service.ms.ProjectTechService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/23 15:51
 * description:技改详情接口
 */
@Api(tags = "技改详情接口", value = "技改详情接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectTechLog")
public class ProjectTechLogController extends BaseController {

    @Autowired
    private ProjectTechService projectTechService;

    @GetMapping("/getTechList")
    @PermissionRequired(perms = "project:tech:search")
    @ApiOperation(value = "获取项目技改进度列表", notes = "获取项目技改进度列表")
    public PageModel<List<ProjectTechProgressModel>> getTechList(QueryTechModel query) throws OwnerException {
        return projectTechService.getTechList(query, getUserInfo(), getDataPerm());
    }

    @GetMapping("/getLogList")
    @PermissionRequired(perms = "project:tech:search")
    @ApiOperation(value = "获取技改日志列表", notes = "获取技改日志列表")
    public List<ProjectTechLogModel> getLogList(Integer projectId) throws OwnerException {
        return projectTechService.getLogList(projectId);
    }

    @PostMapping("/addTechLog")
    @PermissionRequired(perms = "project:tech:add")
    @SystemLog(description = "添加技改进度日志", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加技改进度日志", notes = "添加技改进度日志")
    public Boolean addTechLog(@RequestBody @Validated ProjectTechLogModel model) throws OwnerException {
        return projectTechService.addTechLog(model, getUserInfo());
    }

    @PostMapping("/updateTechLog")
    @PermissionRequired(perms = "project:tech:edit")
    @SystemLog(description = "更新技改进度日志", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新技改进度日志", notes = "更新技改进度日志")
    public Boolean updateTechLog(@RequestBody @Validated ProjectTechLogModel model) throws OwnerException {
        return projectTechService.updateTechLog(model, getUserInfo());
    }

    @PostMapping("/delTechLog")
    @PermissionRequired(perms = "project:tech:del")
    @SystemLog(description = "删除技改进度日志", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除技改进度日志", notes = "删除技改进度日志")
    public Boolean delTechLog(@RequestBody @Validated ProjectTechLogModel model) throws OwnerException {
        return projectTechService.delTechLog(model, getUserInfo());
    }

}
