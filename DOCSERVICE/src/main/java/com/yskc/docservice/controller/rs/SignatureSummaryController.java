package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.SignatureExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.DocFileFooterService;
import com.yskc.docservice.service.rs.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * @DateTime: 2022/3/7 8:49
 * @Description:
 * @author: wjy
 */
@Api(tags = "项目签名汇总接口", value = "项目签名汇总接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/signatureSummary")
public class SignatureSummaryController extends RsBaseController{

    @Autowired
    private DocFileFooterService docFileFooterService;
    @Autowired
    private DocServiceConfig docServiceConfig;
    @Autowired
    private ExcelService excelService;

    @SystemLog(description = "导入项目签名汇总", mode = SystemLog.SAVE_DB)
    @PostMapping("/importSignature")
    @PermissionRequired(perms = "project:signatureSummary:import")
    @ApiOperation(value = "导入项目签名汇总", notes = "导入项目签名汇总", response = String.class)
    public String importSignature(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<SignatureExcel> excelResult = excelService.getExcelResult(tempPath, file, SignatureExcel.class, tableField);
        String msg = excelResult.msg;
        if (StringUtils.hasLength(msg)){
            return msg;
        }else {
            return docFileFooterService.importSignature(info, excelResult.getData(), year);
        }
    }
}
