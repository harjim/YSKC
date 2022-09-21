package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.SalaryExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.SalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;


/**
 * 员工薪资类接口
 *
 * @author huronghua
 */
@Api(tags = "员工薪资类接口", value = "员工薪资类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/salary")
public class SalaryController extends RsBaseController {

    @Autowired
    private SalaryService salaryService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/importSalary")
    @PermissionRequired(perms = "dataentry:salary:import")
    @ApiOperation(value = "导入员工薪资", notes = "导入员工薪资", response = String.class)
    public String importSalary(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<SalaryExcel> excelResult = excelService.getExcelResult(tempPath, file, SalaryExcel.class, tableField);
        salaryService.importSalary(info, excelResult.getData());
        return excelResult.getMsg();
    }
}
