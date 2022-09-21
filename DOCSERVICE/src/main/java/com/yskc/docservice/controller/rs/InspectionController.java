package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.InspectionExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.InspectionService;
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
 * @CreateTime: 2019-07-11 16:03
 * @Description: 费用录入controller层
 */
@Api(tags = "费用录入类接口", value = "费用录入类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/inspection")
public class InspectionController extends RsBaseController {

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param type
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importInspection")
    @SystemLog(description = "导入费用录入", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入费用录入", notes = "导入费用录入", response = String.class)
    public String importInspection(@RequestParam("file") MultipartFile file, Integer type, TableField tableField, Integer year) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InspectionExcel> excelResult = excelService.getExcelResult(tempPath, file, InspectionExcel.class, tableField);
        String msg = inspectionService.importInspection(userInfo, excelResult.getData(), type, year);
        StringBuilder stringBuilder = new StringBuilder(excelResult.getMsg());
        stringBuilder.append(msg);
        return stringBuilder.toString();
    }
}
