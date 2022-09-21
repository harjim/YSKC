package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.InitEquipmentExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.InitEquipmentService;
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
 * @CreateTime: 2019-07-23 17:09
 * @Description: 设备清单controller层
 */
@Api(tags = "设备清单接口", value = "设备清单接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/initEquipment")
public class InitEquipmentController extends RsBaseController {

    @Autowired
    private InitEquipmentService initEquipmentService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @Autowired
    private ExcelService excelService;

    @PostMapping("/importInitEquipment")
    @PermissionRequired(perms = "project:report:equipment:import")
    @SystemLog(description = "导入项目设备清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入项目设备清单", notes = "导入项目设备清单")
    public String importInitEquipment(@RequestParam("file") MultipartFile file, TableField tableField, int year, Integer projectId) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InitEquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, InitEquipmentExcel.class, tableField);
        initEquipmentService.importInitEquipment(userInfo, excelResult.getData(), year, projectId);
        return excelResult.msg;
    }
}
