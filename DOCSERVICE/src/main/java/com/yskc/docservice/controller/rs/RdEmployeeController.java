package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ImportEmployeeExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.RdEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-11-18 15:13
 * @Description: RdEmployeeController
 */
@Api(tags = "研发花名册接口", value = "研发花名册接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/rdEmployee")
public class RdEmployeeController extends RsBaseController {

    @Autowired
    private RdEmployeeService rdEmployeeService;
    @Autowired
    private DocServiceConfig docServiceConfig;


    /**
     * @param file
     * @param year
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEmployee")
    @PermissionRequired(perms = "rdorg:rdEmployee:import")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importEmployee(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ImportEmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, ImportEmployeeExcel.class, tableField);
        return rdEmployeeService.importEmployee(info, excelResult.getData(), year);
    }
}
