
package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.FinancialConditionExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.FinancialConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-09-25 15:28
 * @Description: FinancialConditionController
 */
@Api(tags = "财务状况", value = "财务状况")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/financialCondition")
public class FinancialConditionController extends RsBaseController {
    @Autowired
    private FinancialConditionService financialConditionService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DocServiceConfig docServiceConfig;


    /**
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入财务状况",mode = SystemLog.SAVE_DB)
    @PostMapping("/importFinancialCondition")
    @PermissionRequired(perms = "company:financialCondition:import")
    @ApiOperation(value = "导入财务状况", notes = "导入财务状况", response = String.class)
    public String importFinancialCondition(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<FinancialConditionExcel> excelResult = excelService.getExcelResult(tempPath, file, FinancialConditionExcel.class, tableField);
        financialConditionService.importFinancialCondition(info, excelResult.getData());
        return excelResult.getMsg();
    }


}
