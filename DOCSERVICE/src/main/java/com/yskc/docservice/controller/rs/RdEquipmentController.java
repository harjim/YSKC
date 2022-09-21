package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.service.rs.RdEquipmentService;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
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
 * @CreateTime: 2019-12-02 09:11
 * @Description: 研发设备接口
 */
@Api(tags = "研发设备接口", value = "研发设备接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/rdEquipment")
public class RdEquipmentController extends RsBaseController {

    @Autowired
    private RdEquipmentService rdEquipmentService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param year
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入研发设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdEquipment")
    @PermissionRequired(perms = "rdorg:rdEquipment:import")
    @ApiOperation(value = "导入研发设备", notes = "导入研发设备", response = String.class)
    public String importEmployee(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<EquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, EquipmentExcel.class, tableField);
        return rdEquipmentService.importRdEquipment(info, excelResult.getData(), year);
    }
}
