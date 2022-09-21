package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.CustomerAttendanceExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.CustomerAttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:36
 * @Description: 员工考勤接口
 */
@RestController
@Api(tags = "员工考勤接口", value = "员工考勤接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/doc/customerAttendance")
public class CustomerAttendanceController extends RsBaseController {

    @Autowired
    private CustomerAttendanceService customerAttendanceService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/importAttendance")
    @PermissionRequired(perms = "dataentry:customerAttendance:import")
    @ApiOperation(notes = "导入人员考勤", value = "导入人员考勤")
    public String importAttendance(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<CustomerAttendanceExcel> customerAttendanceExcels = excelService.getExcelResult(tempPath, file, CustomerAttendanceExcel.class, tableField);
        customerAttendanceService.importAttendance(userInfo, customerAttendanceExcels.getData());
        return customerAttendanceExcels.msg;
    }
}
