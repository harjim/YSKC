package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.HighTechIncomeModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.HighTechIncomeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:21
 * @Description: 高品收入
 */
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/highTechIncome")
public class HighTechIncomeController extends RsBaseController {

    @Autowired
    private HighTechIncomeService highTechIncomeService;
    @Autowired
    private DocServiceConfig docServiceConfig;


    @SystemLog(description = "导入高品收入",mode = SystemLog.SAVE_DB)
    @PostMapping("/importIncome")
    @PermissionRequired(perms = "highTech:highTechIncome:import")
    @ApiOperation(value = "导入高品收入", notes = "导入高品收入")
    public String importIncome(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<HighTechIncomeModel> excelResult = excelService.getExcelResult(tempPath, file, HighTechIncomeModel.class, tableField);
        highTechIncomeService.importIncome(userInfo, excelResult.getData());
        return excelResult.msg;
    }
}
