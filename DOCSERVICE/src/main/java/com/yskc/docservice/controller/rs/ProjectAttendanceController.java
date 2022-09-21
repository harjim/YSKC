package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ProjectAttendanceExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ProjectAttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Date;

/**
 * 研发人员类接口
 *
 * @author huronghua
 */
@Api(tags = "研发人员考勤类接口", value = "研发人员考勤类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/projectAttendance")
public class ProjectAttendanceController extends RsBaseController {
    @Autowired
    private ProjectAttendanceService projectAttendanceService;
    @Autowired
    private DocServiceConfig docServiceConfig;


    @SystemLog(description = "导入研发考勤记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdAttendance")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入研发考勤记录", notes = "导入研发考勤记录")
    public String importRdAttendance(@RequestParam("file") MultipartFile file, Date month, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<ProjectAttendanceExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectAttendanceExcel.class, tableField);
        projectAttendanceService.importRdAttendance(userInfo, excelResult.getData(), month);
        return excelResult.msg;
    }
}
