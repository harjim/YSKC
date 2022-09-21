package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.ratio.EquipmentRatioModel;
import com.yskc.rs.service.WorktimeRatioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-12 11:51
 * @Description: 研发比例
 */
@Api(tags = "研发比例配置层接口", value = "研发比例配置层接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/worktimeRatio")
public class WorktimeRatioController extends BaseController {
    @Autowired
    private WorktimeRatioService worktimeRatioService;

    /**
     *
     * @param equipmentRatioModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "配置设备研发比例")
    @ApiOperation(notes = "配置设备研发比例", value = "配置设备研发比例")
    @PostMapping("/setEquipmentRatio")
    public Boolean setEquipmentRatio(@RequestBody @Validated EquipmentRatioModel equipmentRatioModel) throws OwnerException {
        return worktimeRatioService.setEquipmentRatio(getUserInfo(), equipmentRatioModel);
    }

}
