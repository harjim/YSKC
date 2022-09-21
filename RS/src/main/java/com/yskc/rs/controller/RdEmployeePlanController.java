package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employeePlan.PlanTimeModel;
import com.yskc.rs.models.employeePlan.QueryPlanModel;
import com.yskc.rs.models.employeePlan.RdEmployeePlanModel;
import com.yskc.rs.models.employeePlan.RdEmployeePlanTotalModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.RdEmployeePlanExcel;
import com.yskc.rs.models.excel.RdPlanExcel;
import com.yskc.rs.service.RdEmployeePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/16 17:26
 * description:研发人员计划接口
 */
@Api(tags = "研发人员计划接口", value = "研发人员计划接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rdEmployeePlan")
public class RdEmployeePlanController extends BaseController {

    @Autowired
    private RdEmployeePlanService employeePlanService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:rdEmployeePlan:search")
    @ApiOperation(notes = "获取研发人员计划", value = "获取研发人员计划")
    public PageModel<List<PlanTimeModel>> getList(QueryPlanModel query) throws OwnerException {
        return employeePlanService.getList(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getTotalList")
    @PermissionRequired(perms = "project:rdEmployeePlan:search")
    @ApiOperation(notes = "获取研发人员月总计划工时", value = "获取研发人员月总计划工时")
    public PageModel<List<RdEmployeePlanTotalModel>> getTotalList(QueryPlanModel query) throws OwnerException {
        return employeePlanService.getTotalList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param models
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存人员计划", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:rdEmployeePlan:save")
    @ApiOperation(value = "保存人员计划", notes = "保存人员计划", response = boolean.class)
    public boolean add(@RequestBody @Validated List<RdEmployeePlanModel> models) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeePlanService.save(userInfo, models);
    }


    @SystemLog(description = "导入研发工时计划", mode = SystemLog.SAVE_DB)
    @PostMapping("/importPlan")
    @PermissionRequired(perms = "project:rdEmployeePlan:import")
    @ApiOperation(value = "导入研发工时计划", notes = "导入研发工时计划", response = String.class)
    public String importPlan(@RequestParam("file") MultipartFile file, TableField tableField, int year, Integer projectId) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<RdEmployeePlanExcel> excelResult = excelService.getExcelResult(tempPath, file, RdEmployeePlanExcel.class, tableField);
        employeePlanService.importPlan(info, excelResult.getData(), year, projectId);
        return excelResult.getMsg();
    }

    @SystemLog(description = "导入研发工时计划", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdPlan")
    @PermissionRequired(perms = "project:rdEmployeePlan:import")
    @ApiOperation(value = "导入研发工时计划", notes = "导入研发工时计划", response = String.class)
    public String importRdPlan(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<RdPlanExcel> excelResult = excelService.getExcelResult(tempPath, file, RdPlanExcel.class, tableField);
        employeePlanService.importPlanTime(info, excelResult.getData());
        return excelResult.getMsg();
    }

    @SystemLog(description = "导出研发人员计划", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportPlan")
    @PermissionRequired(perms = "project:rdEmployeePlan:export")
    @ApiOperation(value = "导出研发人员计划", notes = "导出研发人员计划")
    public void exportPlan(Integer year, Date[] months) throws IOException, OwnerException {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0,number,#}年{1}研发人员计划表.xls", year, info.getCompanyName()))) {
            employeePlanService.exportPlan(year, months, info, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }

    }
}
