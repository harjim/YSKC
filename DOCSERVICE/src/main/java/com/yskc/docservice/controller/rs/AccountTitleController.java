package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.AccountExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.AccountTitleService;
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
 * @CreateTime: 2019-11-23 09:34:08
 * @Description: 科目类接口
 */
@Api(tags = "科目类接口", value = "科目类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/accountTitle")
public class AccountTitleController extends RsBaseController {

    @Autowired
    private AccountTitleService accountTitleService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importAccount")
    @SystemLog(description = "导入科目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入科目", notes = "导入科目")
    @PermissionRequired(perms = "company:account:import")
    public String importAccount(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<AccountExcel> excelResult = excelService.getExcelResult(tempPath, file, AccountExcel.class, tableField);
        String error = accountTitleService.importAccount(userInfo, excelResult.getData());
        return excelResult.msg + error;
    }
}
