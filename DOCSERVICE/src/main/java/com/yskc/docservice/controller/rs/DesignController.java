package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DesginExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.DesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;


/**
 * 研发设计类接口
 *
 * @author huronghua
 */
@Api(tags = "研发设计类接口", value = "研发设计类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/design")
public class DesignController extends RsBaseController {
    @Autowired
    private DesignService designService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * 导入设计费用
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importDesignData")
    @SystemLog(description = "导入设计费用", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:design:import")
    @ApiOperation(value = "导入设计费用", notes = "导入设计费用", response = String.class)
    public String importDesignData(@RequestParam("file") MultipartFile file, TableField tableField, Integer year) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<DesginExcel> excelResult = excelService.getExcelResult(tempPath, file, DesginExcel.class, tableField);
        designService.importDesign(userInfo, excelResult.getData(), year);
        return excelResult.getMsg();

    }
}
