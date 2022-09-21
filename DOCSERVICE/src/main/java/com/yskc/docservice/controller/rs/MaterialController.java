package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.MaterialExcel;
import com.yskc.docservice.models.rs.excel.MaterialTypeExcel;
import com.yskc.docservice.models.rs.material.ImportMaterialModel;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * 数据录入物料类接口
 *
 * @author huronghua
 */
@Api(tags = "数据录入物料类接口", value = "数据录入物料类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/material")
public class MaterialController extends RsBaseController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入物料清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/importMaterial")
    @PermissionRequired(perms = "dataentry:materialRaw:import,dataentry:materialDataentry:import,dataentry:materialReserve:import")
    @ApiOperation(value = "导入物料清单", notes = "导入物料清单", response = String.class)
    public ImportMaterialModel importMaterial(@RequestParam("file") MultipartFile file, MaterialTypeExcel tableField) throws OwnerException {
        return importMaterial(file, tableField, false);
    }

    @SystemLog(description = "导入研发物料清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/rdImportMaterial")
    @PermissionRequired(perms = "dataentry:materialRaw:rdImport,dataentry:materialDataentry:rdImport,dataentry:materialReserve:rdImport")
    @ApiOperation(value = "导入研发物料清单", notes = "导入研发物料清单", response = String.class)
    public ImportMaterialModel rdImportMaterial(@RequestParam("file") MultipartFile file, MaterialTypeExcel tableField) throws OwnerException {
        return importMaterial(file, tableField, true);
    }

    private ImportMaterialModel importMaterial(MultipartFile file, MaterialTypeExcel tableField, Boolean isRD) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<MaterialExcel> excelResult = excelService.getExcelResult(tempPath, file, MaterialExcel.class, tableField);
        return materialService.importMaterial(info, excelResult.getData(), tableField.getType(), tableField.getYear(), isRD);
    }

    @SystemLog(description = "确认导入物料清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/confirmImport")
    @PermissionRequired(perms = "dataentry:materialRaw:import,dataentry:materialDataentry:import,dataentry:materialReserve:import")
    @ApiOperation(value = "确认导入物料清单", notes = "确认导入物料清单", response = String.class)
    public String confirmImport(@RequestParam("file") MultipartFile file, MaterialTypeExcel tableField) throws OwnerException {
        return confirmImport(file, tableField, false);
    }

    @SystemLog(description = "确认导入研发物料清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/rdConfirmImport")
    @PermissionRequired(perms = "dataentry:materialRaw:rdImport,dataentry:materialDataentry:rdImport,dataentry:materialReserve:rdImport")
    @ApiOperation(value = "确认导入研发物料清单", notes = "确认导入研发物料清单", response = String.class)
    public String rdConfirmImport(@RequestParam("file") MultipartFile file, MaterialTypeExcel tableField) throws OwnerException {
        return confirmImport(file, tableField, true);
    }

    private String confirmImport(MultipartFile file, MaterialTypeExcel tableField, Boolean isRD) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<MaterialExcel> excelResult = excelService.getExcelResult(tempPath, file, MaterialExcel.class, tableField);
        materialService.confirmImport(info, excelResult.getData(), tableField.getType(), tableField.getYear(), isRD);
        return excelResult.getMsg();
    }


}
