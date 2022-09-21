package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.TechEquipmentModel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.TechEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:08
 * @Description:备案清单
 */
@Api(tags = "技改资料清单接口", value = "技改资料清单接口")
@CrossOrigin(originPatterns = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/doc/techEquipment")
public class TechEquipmentController extends RsBaseController {

    @Autowired
    private TechEquipmentService techEquipmentService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @PostMapping("/importEquipment")
    @PermissionRequired(perms = "tech:beian:beianList:import")
    @SystemLog(description = "导入备案清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "导入备案清单", value = "导入备案清单")
    public String importEquipment(@RequestParam("file") MultipartFile file, TableField tableField, Integer beianId) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<TechEquipmentModel> excelResult = excelService.getExcelResult(tempPath, file, TechEquipmentModel.class, tableField);
        techEquipmentService.importEquipment(info, excelResult.getData(), beianId);
        return excelResult.getMsg();
    }
}
