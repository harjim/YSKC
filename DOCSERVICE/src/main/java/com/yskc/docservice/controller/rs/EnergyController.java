package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EnergyExcel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.EnergyService;
import com.yskc.docservice.service.rs.ExcelService;
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
 * @Description: 能源类接口
 */

@Api(tags = "能源类接口", value = "能源类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/energy")
public class EnergyController extends RsBaseController {

    @Autowired
    private EnergyService energyService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param type
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEnergy")
    @PermissionRequired(perms = "dataentry:stimulus:import,dataentry:fuel:import")
    @ApiOperation(value = "导入能源", notes = "导入能源")
    public String importEnergy(@RequestParam("file") MultipartFile file, Integer type, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<EnergyExcel> excelResult = excelService.getExcelResult(tempPath, file, EnergyExcel.class, tableField);
        energyService.importEnergy(userInfo, excelResult.getData(), type, year);
        return excelResult.msg;
    }
}
