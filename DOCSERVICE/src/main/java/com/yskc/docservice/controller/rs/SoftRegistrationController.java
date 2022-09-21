package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.SoftRegistrationExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.SoftRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @DateTime: 2021/11/2 14:25
 * @Description:计算机软件著作类接口
 * @author: hsx
 */
@Api(tags = "计算机软件著作类接口", value = "计算机软件著作类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/softRegistration")
public class SoftRegistrationController extends RsBaseController{

    @Autowired
    private SoftRegistrationService softRegistrationService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入计算机软件著作列表",mode = SystemLog.SAVE_DB)
    @PostMapping("/import")
    @PermissionRequired(perms = "project:softRegistration:import")
    @ApiOperation(value = "导入计算机软件著作列表", notes = "导入计算机软件著作列表", response = String.class)
    public Boolean importSoftRegistration(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<SoftRegistrationExcel> excelResult= excelService.getExcelResult(tempPath, file, SoftRegistrationExcel.class, tableField);
        return softRegistrationService.importSoftRegistration(info, excelResult.getData());
    }

}
