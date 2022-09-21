package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.entity.project.ProjectRdHourConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectattendance.RefreshAttendance;
import com.yskc.rs.models.projectequipment.BatchEquipmentModel;
import com.yskc.rs.models.projectequipment.ProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.service.ProjectEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 15:07
 * @Description: 设备项目研发记录controller层
 */
@Api(value = "项目设备研发记录", tags = "项目设备研发记录")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/projectEquipment")
public class ProjectEquipmentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectEquipmentService projectEquipmentService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取设备研发使用记录列表", notes = "获取设备研发使用记录列表")
    public PageModel<List<ProjectEquipmentModel>> getList(QueryProjectEquipmentModel query) throws OwnerException {
        return projectEquipmentService.getList(getUserInfo().getCompanyId(), query);
    }

    @SystemLog(description = "刷新设备研发使用记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/refresh")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "刷新设备研发使用记录", notes = "刷新设备研发使用记录")
    public Boolean refresh(@RequestBody @Validated RefreshAttendance fresh) throws OwnerException {
        return projectEquipmentService.refresh(getUserInfo(), fresh);
    }

    @SystemLog(description = "保存设备研发使用记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "保存设备研发使用记录", notes = "保存设备研发使用记录")
    public Boolean saveList(@RequestBody @Validated BatchEquipmentModel batch) throws OwnerException {
        return projectEquipmentService.saveList(getUserInfo(), batch);
    }

    @GetMapping("/exportData")
    @SystemLog(description = "导出设备研发使用记录", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "导出设备研发使用记录", notes = "导出设备研发使用记录")
    public void exportData(QueryProjectEquipmentModel query) throws Exception {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(info.getCompanyName() + "研发设备工作记录.xlsx")) {
            projectEquipmentService.exportData(info, query,out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("导出失败," + e.getMessage());
        }
    }

    @GetMapping("/getAttData")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取对应月份天数是否存在人员研发记录", notes = "获取对应月份天数是否存在人员研发记录")
    public Map<Integer, Integer> getAttData(QueryProjectEquipmentModel query) {
        return projectEquipmentService.getAttData(query);
    }

    @GetMapping("/getRdHourConfig")
    @ApiOperation(value = "获取研发工时配置", notes = "获取研发工时配置")
    public String getRdHourConfig(ProjectRdHourConfig rdHourConfig) throws OwnerException {
        return projectEquipmentService.getRdHourConfig(rdHourConfig, getUserInfo().getCompanyId());
    }
}
