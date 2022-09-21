package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DataEquipmentExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.DataEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
/**
 * 设备使用类接口
 *
 * @author huronghua
 */
@Api(tags = "设备使用类接口", value = "设备使用类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/dEquipment")
public class DataEquipmentController extends RsBaseController {
    @Autowired
    private DataEquipmentService dequipmentService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * 导入设备使用
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入设备使用",mode = SystemLog.SAVE_DB)
    @PostMapping("/importDEquipment")
    @PermissionRequired(perms = "dataentry:equipment:import")
    @ApiOperation(value = "导入设备使用", notes = "导入设备使用")
    public String importInfo(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<DataEquipmentExcel> dataEquipmentExcels = excelService.getExcelResult(tempPath, file, DataEquipmentExcel.class, tableField);
        dequipmentService.importInfo(userInfo, dataEquipmentExcels.getData());
        return dataEquipmentExcels.msg;
    }
}
