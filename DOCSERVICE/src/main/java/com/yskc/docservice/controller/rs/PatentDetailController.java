package com.yskc.docservice.controller.rs;

/**
 * Created by hck
 * on 2020/6/29 11:39
 * description:专利详情
 */

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.PatentDetailExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.PatentDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

@Api(tags = "专利详情接口", value = "专利详情接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/patentDetail")
public class PatentDetailController extends RsBaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentDetailService patentDetailService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入专利列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/importPatent")
    @PermissionRequired(perms = "project:patent:list:import")
    @ApiOperation(value = "导入专利列表", notes = "导入专利列表", response = String.class)
    public String importPatent(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<PatentDetailExcel> excelResult = excelService.getExcelResult(tempPath, file, PatentDetailExcel.class, tableField);
        return patentDetailService.importPatent(info, excelResult.getData());
    }
}
