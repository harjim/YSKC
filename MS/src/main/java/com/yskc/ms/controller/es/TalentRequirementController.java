package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.newexpert.talentdelivery.QueryTalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentdelivery.TalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsQueryTechRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsTechRequirementModel;
import com.yskc.ms.service.es.TalentRequirementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description: 人才需求
 * @author: cyj
 * @create: 2021-12-08 10:30
 **/
@RequestMapping("/api/TalentRequirement")
@RestController
@Api(tags = "人才需求接口层", value = "人才需求接口层")
public class TalentRequirementController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TalentRequirementService talentRequirementService;

    @GetMapping("/getList")
    @ApiOperation(value = "获取人才需求列表", notes = "获取人才需求列表")
    @PermissionRequired(perms = "es:talentRequirement:search")
    public PageModel<List<TalentRequirementModel>> getList(QueryTalentRequirementModel query) {
        return talentRequirementService.getList(query);
    }

    @GetMapping("/getData")
    @ApiOperation(value = "获取人才需求列表", notes = "获取人才需求列表")
    @PermissionRequired(perms = "es:talentRequirement:search")
    public TalentRequirementModel getData(@RequestParam("id") Integer id){
        return talentRequirementService.getData(id);
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "改变人才需求状态", notes = "改变人才需求状态")
    @SystemLog(description = "改变人才需求状态", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:talentRequirement:issue,es:talentRequirement:recall")
    public Boolean changeStatus(@RequestBody TalentRequirementModel model) throws OwnerException {
        return talentRequirementService.changeStatus(model.getId(), model.getStatus(), getUserInfo().getId());
    }

    @PostMapping("/save")
    @ApiOperation(value = "添加/编辑人才需求", notes = "添加/编辑人才需求")
    @SystemLog(description = "添加/编辑人才需求", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:talentRequirement:add,es:talentRequirement:edit,es:talentRequirement:issue")
    public Boolean save(@RequestBody TalentRequirementModel model) throws OwnerException {
        return talentRequirementService.save(model, getUserInfo().getId());
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除人才需求", notes = "删除人才需求")
    @SystemLog(description = "删除人才需求", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:talentRequirement:del")
    public Boolean del(@RequestBody @Validated Integer[] ids) throws OwnerException {
        return talentRequirementService.del(ids);
    }

    @GetMapping("/getDeliveryList")
    @ApiOperation(value = "获取投递列表", notes = "获取投递列表")
    @PermissionRequired(perms = "es:talentDelivery:search")
    public PageModel<List<TalentDeliveryModel>> getDeveliryList(QueryTalentDeliveryModel query) {
        return talentRequirementService.getDeliveryList(query);
    }

}
