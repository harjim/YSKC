package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ICRegistrationExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ICRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @DateTime: 2021/11/2 14:24
 * @Description:集成电路设计类接口
 * @author: hsx
 */
@Api(tags = "集成电路设计类接口", value = "集成电路设计类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/ICRegistration")
public class ICRegistrationController extends RsBaseController{
    @Autowired
    private ICRegistrationService iCRegistrationService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入集成电路设计列表",mode = SystemLog.SAVE_DB)
    @PostMapping("/import")
    @PermissionRequired(perms = "project:ICRegistration:import")
    @ApiOperation(value = "导入集成电路设计列表", notes = "导入集成电路设计列表", response = Boolean.class)
    public Boolean importICRegistration(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ICRegistrationExcel> excelResult= excelService.getExcelResult(tempPath, file, ICRegistrationExcel.class, tableField);
        return iCRegistrationService.importICRegistration(info, excelResult.getData());
    }

}
