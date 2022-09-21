package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.ProjectFinaSchedule;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ProjectFinaScheduleExcel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleAggModel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectfinaschedule.QueryProjectFinaScheduleModel;
import com.yskc.rs.service.ProjectFinaScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:15
 * @Description: 项目试验试制实际工时controller层
 */
@Api(value = "项目试验试制实际工时表接口", tags = "项目试验试制实际工时表接口")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RequestMapping("/api/projectFinaSchedule")
public class ProjectFinaScheduleController extends BaseController {

    @Autowired
    private ProjectFinaScheduleService projectFinaScheduleService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:finaSchedule:view")
    @ApiOperation(value = "获取项目试验试制实际工时表", notes = "获取项目试验试制实际工时表")
    public PageModel<List<ProjectFinaScheduleModel>> getList(QueryProjectFinaScheduleModel query) throws OwnerException {
        return projectFinaScheduleService.getList(query, getUserInfo().getCompanyId());
    }

    @SystemLog(description = "添加试验试制实际工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:finaSchedule:add")
    @ApiOperation(value = "添加试验试制实际工时", notes = "添加试验试制实际工时")
    public Boolean add(@RequestBody @Validated ProjectFinaSchedule entity) throws OwnerException {
        return projectFinaScheduleService.add(entity, getUserInfo());
    }

    @SystemLog(description = "编辑试验试制实际工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:finaSchedule:edit")
    @ApiOperation(value = "编辑试验试制实际工时", notes = "编辑试验试制实际工时")
    public Boolean edit(@RequestBody @Validated ProjectFinaSchedule entity) throws OwnerException {
        return projectFinaScheduleService.edit(entity, getUserInfo());
    }

    @SystemLog(description = "删除试验试制实际工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:finaSchedule:delete")
    @ApiOperation(value = "删除试验试制实际工时", notes = "删除试验试制实际工时")
    public Boolean del(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return projectFinaScheduleService.del(deleteModel.getIds());
    }

    @SystemLog(description = "导入试验试制实际工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importFinaSchedule")
    @PermissionRequired(perms = "project:finaSchedule:import")
    @ApiOperation(value = "导入试验试制实际工时", notes = "导入试验试制实际工时")
    public Boolean importFinaSchedule(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectFinaScheduleExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectFinaScheduleExcel.class, tableField);
        return projectFinaScheduleService.importFinaSchedule(info, excelResult.getData());
    }


    @GetMapping("/exportFinaSchedule")
    @PermissionRequired(perms = "project:finaSchedule:export")
    @ApiOperation(value = "导出试验试制实际工时", notes = "导出试验试制实际工时")
    public void exportOutsourcing(QueryProjectFinaScheduleModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        Map<String, Object> dataMap = projectFinaScheduleService.getExportFinaSchedule(query, info.getCompanyId());
        Integer year = query.getYear();
        String title = info.getCompanyName() + year + "试验试制实际工时";
        dataMap.put("title", title);
        dataMap.put("year", year);
        exportFileByTemplate(dataMap, Constant.TEMPLATE_DIR + "试验试制实际工时模板.xlsx", title + ".xlsx");
    }

    @SystemLog(description = "试验试制实际工时归集费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/aggFee")
    @PermissionRequired(perms = "project:finaSchedule:agg")
    @ApiOperation(value = "试验试制实际工时归集费用", notes = "试验试制实际工时归集费用")
    public Boolean aggFee(@RequestBody @Validated ProjectFinaScheduleAggModel agg) throws OwnerException {
        return projectFinaScheduleService.aggFee(agg, getUserInfo());
    }
}
