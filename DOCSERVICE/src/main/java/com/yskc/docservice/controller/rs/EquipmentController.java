package com.yskc.docservice.controller.rs;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.EquipmentService;
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
 * @CreateTime: 2019-07-09 09:23
 * @Description: 设备控制层
 */

@Api(tags = "设备类接口", value = "设备类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/equipment")
public class EquipmentController extends RsBaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEquipment")
    @PermissionRequired(perms = "company:equipment:import")
    @ApiOperation(value = "导入设备", notes = "导入设备")
    public String importEquipment(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<EquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, EquipmentExcel.class, tableField);
        String error = equipmentService.importEquipment(userInfo, excelResult.getData());
        if (!StrUtil.isEmpty(error)) {
            return excelResult.msg + error;
        }
        return excelResult.msg;
    }
}
