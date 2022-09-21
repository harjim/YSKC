package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.projectenergy.*;
import com.yskc.rs.service.ProjectEnergyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yezhihao
 * @version 创建时间：2019年7月17日 下午3:42:30
 */
@Api(tags = "数据归集能源类接口", value = "数据归集能源类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectEnergy")
public class ProjectEnergyController extends BaseController {

    @Autowired
    private ProjectEnergyService projectEnergyService;

    /**
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryProjectEnergy")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "查询项目使用能源", notes = "查询项目使用能源")
    public PageResult queryProjectEnergy(QueryProjectEnergy queryEnergy) throws OwnerException {
        return projectEnergyService.queryProjectEnergy(getUserInfo().getCompanyId(), queryEnergy);
    }

    /**
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEnergyList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "查询能源损耗", notes = "查询能源损耗")
    public PageResult getEnergyList(QueryProjectEnergy queryEnergy) throws OwnerException {
        return projectEnergyService.getEnergyList(getUserInfo().getCompanyId(), queryEnergy);
    }

    /**
     * @param energyAddListModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量添加项目能源损耗", mode = SystemLog.SAVE_DB)
    @PostMapping("/addList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量添加项目能源损耗", notes = "批量添加项目能源损耗")
    public boolean addList(@RequestBody @Validated EnergyAddListModel energyAddListModel) throws OwnerException {
        return projectEnergyService.addList(getUserInfo(), energyAddListModel);
    }

    /**
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "按条件添加项目能源损耗", mode = SystemLog.SAVE_DB)
    @PostMapping("/addByCondition")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "按条件添加项目能源损耗", notes = "按条件添加项目能源损耗")
    public boolean addByCondition(@RequestBody @Validated QueryProjectEnergy queryEnergy) throws OwnerException {
        return projectEnergyService.addByCondition(getUserInfo(), queryEnergy);
    }

    /**
     * @param delBatchModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除项目能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/delEnergys")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量删除项目能源", notes = "批量删除项目能源")
    public boolean delEnergys(@RequestBody @Validated BatchProjectEnergyModel delBatchModel) throws OwnerException {
        return projectEnergyService.delEnergies(getUserInfo(), delBatchModel);
    }

    /**
     * @param updateProjectEnergy
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量更新项目能源费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量更新项目能源费用", notes = "批量更新项目能源费用")
    public boolean updateList(@RequestBody @Validated UpdateProjectEnergy updateProjectEnergy) throws OwnerException {
        return projectEnergyService.updateList(getUserInfo(), updateProjectEnergy,true);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "能源同步折旧工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/syncDepreciation")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "能源同步折旧工时", notes = "能源同步折旧工时")
    public boolean syncDepreciation(@RequestBody @Validated QueryProjectEnergy query) throws OwnerException {
        return projectEnergyService.syncDepreciation(getUserInfo(), query);
    }

    @SystemLog(description = "设置能源研发工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/setRdHour")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "设置能源研发工时", notes = "设置能源研发工时")
    public boolean setRdHour(@RequestBody @Validated UpdateProjectEnergy update) throws OwnerException {
        return projectEnergyService.setRdHour(getUserInfo(), update);
    }

    /**
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEnergies")
    @ApiOperation(value = "查询研发试制，动力明细表", notes = "查询研发试制，动力明细表")
    public PageModel<List<HighTechProjectEnergyModel>> getEnergies(QueryProjectEnergy queryEnergy) throws OwnerException {
        return projectEnergyService.getEnergies(getUserInfo().getCompanyId(), queryEnergy);
    }
}
