package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.RdPlanExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.models.rs.rdplan.RdPlanTableField;
import com.yskc.docservice.service.rs.RdEmployeePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * Created by hck
 * on 2020/11/16 17:26
 * description:研发人员计划接口
 */
@Api(tags = "研发人员计划接口", value = "研发人员计划接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/rdEmployeePlan")
public class RdEmployeePlanController extends RsBaseController {

    @Autowired
    private RdEmployeePlanService employeePlanService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入研发工时计划", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdPlan")
    @PermissionRequired(perms = "project:rdEmployeePlan:import")
    @ApiOperation(value = "导入研发工时计划", notes = "导入研发工时计划", response = String.class)
    public String importRdPlan(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<RdPlanExcel> excelResult = excelService.getExcelResult(tempPath, file, RdPlanExcel.class, tableField);
        employeePlanService.importPlanTime(info, excelResult.getData());
        return excelResult.getMsg();
    }

    @SystemLog(description = "导入分配研发人员计划工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importAllocation")
    @PermissionRequired(perms = "project:rdEmployeePlan:importAllocation")
    @ApiOperation(value = "导入分配研发人员计划工时", notes = "导入分配研发人员计划工时", response = String.class)
    public String importAllocation(@RequestParam("file") MultipartFile file, RdPlanTableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<RdPlanExcel> excelResult = excelService.getExcelResult(tempPath, file, RdPlanExcel.class, tableField);
        employeePlanService.importAllocation(info, excelResult,tableField);
        return excelResult.getMsg();
    }
}
