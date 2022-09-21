package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.models.StepResultModel;
import com.yskc.ms.models.patent.ParentStepModel;
import com.yskc.ms.models.project.QueryProjectStepModel;
import com.yskc.ms.models.project.StepListModel;
import com.yskc.ms.models.project.StepModel;
import com.yskc.ms.models.project.StepStatusModel;
import com.yskc.ms.service.rs.RsProjectStepService;
import com.yskc.ms.service.rs.RsStepStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/19 17:20
 */
@Api(tags = "配置项目类型步骤", value = "配置项目类型步骤接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsProjectStep")
public class RsProjectStepController extends BaseController {

    @Autowired
    private RsProjectStepService rsProjectStepService;
    @Autowired
    private RsStepStatusService rsStepStatusService;


    @PostMapping("/saveStep")
    @SystemLog(description = "保存步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "保存步骤", notes = "保存步骤")
    public Boolean saveStep(@RequestBody @Validated ParentStepModel[] parentStepModels) throws OwnerException {
        return rsProjectStepService.saveStep(getUserInfo(),parentStepModels);
    }

    @GetMapping("/getStep")
    @SystemLog(description = "查询步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "查询步骤", notes = "查询步骤")
    public StepResultModel getStep(Integer productId) throws OwnerException {
        return rsProjectStepService.getStep(productId);
    }

    @PostMapping("/addStep")
    @SystemLog(description = "添加步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "添加步骤", notes = "添加步骤")
    public StepModel addStep(@RequestBody @Validated QueryProjectStepModel model) throws OwnerException {
        return rsProjectStepService.addStep(getUserInfo(),model);
    }

    @PostMapping("/delStep")
    @SystemLog(description = "删除步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "删除步骤", notes = "删除步骤")
    public Boolean delStep(@RequestBody @Validated QueryProjectStepModel model) throws OwnerException {
        return rsProjectStepService.delStep(model);
    }

    @PostMapping("/editStep")
    @SystemLog(description = "编辑步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "编辑步骤", notes = "编辑步骤")
    public Boolean editStep(@RequestBody @Validated QueryProjectStepModel model) throws OwnerException {
        return rsProjectStepService.editStep(getUserInfo(),model);
    }

    @PostMapping("/editStepStatus")
    @SystemLog(description = "",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "修改步骤状态", notes = "修改步骤状态")
    public Boolean editStepStatus(@RequestBody @Validated StepStatusModel model) throws OwnerException {
        return rsStepStatusService.editStepStatus(model,getUserInfo());
    }

    @PostMapping("/addStepList")
    @SystemLog(description = "批量添加步骤",mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms ="")
    @ApiOperation(value = "批量添加步骤", notes = "批量添加步骤")
    public Boolean addStepList(@RequestBody @Validated List<StepListModel> model) throws OwnerException {
        return rsProjectStepService.addStepList(getUserInfo(),model);
    }

}
