package com.yskc.ms.controller.es;

import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.newexpert.techrequirement.EsQueryTechRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsTechRequirementModel;
import com.yskc.ms.service.es.EsTechRequirementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.xpath.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Administrator
 */
@RequestMapping("/api/esTechRequirement")
@RestController
@Api(tags = "技术需求接口层", value = "技术需求接口层")
public class EsTechRequirementController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsTechRequirementService techRequirementService;

    @GetMapping("/getList")
    @ApiOperation(value = "获取技术需求列表", notes = "获取技术需求列表")
    @PermissionRequired(perms = "es:techRequirement:search")
    public PageModel<List<EsTechRequirementModel>> getList(EsQueryTechRequirementModel query) {
        return techRequirementService.getList(query);
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "改变技术需求状态", notes = "改变技术需求状态")
    @SystemLog(description = "改变技术需求状态", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:techRequirement:audit,es:techRequirement:recall")
    public Boolean changeStatus(@RequestBody EsTechRequirementModel model) throws OwnerException {
        return techRequirementService.editStatus(model, getUserInfo().getId());
    }

    @PostMapping("/save")
    @ApiOperation(value = "编辑技术需求", notes = "编辑技术需求")
    @SystemLog(description = "编辑技术需求", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:techRequirement:add,es:techRequirement:edit,es:techRequirement:issue")
    public Boolean save(@RequestBody EsTechRequirementModel model) throws OwnerException {
        return techRequirementService.save(model, getUserInfo().getId());
    }

    @GetMapping("/getData")
    @ApiOperation(value = "获取技术需求详情", notes = "获取技术需求详情")
    @PermissionRequired(perms = "es:techRequirement:view")
    public EsTechRequirementModel getData(@RequestParam("id") Integer id) {
        return techRequirementService.getData(id);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除技术需求", notes = "删除技术需求")
    @SystemLog(description = "删除技术需求", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:techRequirement:del")
    public Boolean del(@RequestBody @Validated Integer[] ids) throws OwnerException {
        return techRequirementService.del(ids);
    }
}
