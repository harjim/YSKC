package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.StEmployeeExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.StEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-08 08:58
 * @Description: 科技管理人员接口
 */
@Api(tags = "科技管理人员接口", value = "科技管理人员接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/doc/stEmployee")
@RestController
public class StEmployeeController extends RsBaseController {
    @Autowired
    private StEmployeeService stEmployeeService;

    @Autowired
    private DocServiceConfig docServiceConfig;
    @Autowired
    private ExcelService excelService;

    @SystemLog(description = "导入研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importStEmployee")
    @PermissionRequired(perms = "rdorg:stEmployee:import")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importStEmployee(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<StEmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, StEmployeeExcel.class, tableField);
        String msg = excelResult.msg;
        if (StringUtils.hasLength(msg)){
            return msg;
        }else {
            return stEmployeeService.importStEmployee(info, excelResult.getData(), year);
        }
    }
}
