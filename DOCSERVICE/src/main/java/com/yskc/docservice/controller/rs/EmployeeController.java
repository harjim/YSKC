package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EmployeeExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.EmployeeService;
import com.yskc.docservice.service.rs.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * 花名册类接口
 *
 * @author huronghua
 */
@Api(tags = "花名册类接口", value = "花名册类接口")
@RestController
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "",methods = {})
@RequestMapping("/doc/employee")
public class EmployeeController extends RsBaseController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEmployee")
    @PermissionRequired(perms = "company:employee:improt")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importEmployee(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<EmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, EmployeeExcel.class, tableField);
        employeeService.importEmployee(info, excelResult.getData());
        return excelResult.getMsg();
    }

}
