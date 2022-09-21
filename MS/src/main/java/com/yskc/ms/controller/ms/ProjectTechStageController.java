package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.ProjectTechStage.ProjectStageModel;
import com.yskc.ms.models.ProjectTechStage.SetDeadlineModel;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.models.tech.ProjectTechLogModel;
import com.yskc.ms.service.ms.ProjectTechStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/28 15:17
 * description:项目技改阶段
 */
@Api(tags = "项目技改阶段接口", value = "项目技改阶段接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/productTechStage")
public class ProjectTechStageController extends BaseController {

    @Autowired
    private ProjectTechStageService projectTechStageService;


    @GetMapping("/getStageList")
    @PermissionRequired(perms = "project:tech:searchTime")
    @ApiOperation(value = "获取项目技改阶段列表", notes = "获取项目技改阶段列表")
    public List<StageModel> getList(@RequestParam("projectId") Integer projectId, @RequestParam("productId")Integer productId) throws OwnerException {
        return projectTechStageService.getStageList( getUserInfo(), projectId,productId);
    }

    @GetMapping("/queryProjectStage")
    @ApiOperation(value = "获取项目类型阶段配置", notes = "获取项目类型阶段配置")
    @PermissionRequired(perms = "project:tech:add")
    public List<StageModel> queryProjectStage( @RequestParam("productId")Integer productId) throws OwnerException {
        return projectTechStageService.queryProjectStage( getUserInfo(),productId);
    }

    @PostMapping("/editDeadline")
    @PermissionRequired(perms = "project:tech:setTime")
    @SystemLog(description = "编辑项目阶段截止时间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑项目阶段截止时间", notes = "编辑项目阶段截止时间")
    public Boolean editDeadline(@RequestBody @Validated List<ProjectStageModel> models) throws OwnerException {
        return projectTechStageService.editDeadline(models, getUserInfo());
    }


    @PostMapping("/setDeadlines")
    @PermissionRequired(perms = "project:tech:setTime")
    @SystemLog(description = "批量设置项目阶段截止时间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量设置项目阶段截止时间", notes = "批量设置项目阶段截止时间")
    public Boolean setDeadlines(@RequestBody @Validated SetDeadlineModel model) throws OwnerException {
        return projectTechStageService.setDeadlines(model, getUserInfo());
    }

}
