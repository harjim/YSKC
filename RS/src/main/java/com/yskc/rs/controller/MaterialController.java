package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.MaterialExcel;
import com.yskc.rs.models.excel.MaterialTypeExcel;
import com.yskc.rs.models.material.*;
import com.yskc.rs.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * 数据录入物料类接口
 *
 * @author huronghua
 */
@Api(tags = "数据录入物料类接口", value = "数据录入物料类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/material")
public class MaterialController extends BaseController {

    @Autowired
    private MaterialService materialService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryMaterial")
    @PermissionRequired(perms = "dataentry:materialRaw:search,dataentry:materialDataentry:search,dataentry:materialReserve:search")
    @ApiOperation(value = "查询物料", notes = "查询物料", response = String.class)
    public PageModel<List<AppMaterialModel>> queryAll(QueryMaterialModel query) throws OwnerException {
        return materialService.queryMaterial(getUserInfo(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "dataentry:materialRaw:add,dataentry:materialDataentry:add,dataentry:materialReserve:add")
    @ApiOperation(value = "保存物料", notes = "保存物料", response = boolean.class)
    public boolean addMaterial(@RequestBody @Validated MaterialEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return materialService.addMaterial(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "编辑物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "dataentry:materialRaw:edit,dataentry:materialDataentry:edit,dataentry:materialReserve:edit")
    @ApiOperation(value = "编辑物料", notes = "编辑物料", response = boolean.class)
    public boolean editMaterial(@RequestBody @Validated MaterialEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return materialService.editMaterial(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "设置物料研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/editDept")
    @PermissionRequired(perms = "dataentry:materialRaw:edit,dataentry:materialDataentry:edit,dataentry:materialReserve:edit")
    @ApiOperation(value = "设置物料研发部门", notes = "设置物料研发部门", response = boolean.class)
    public boolean editDept(@RequestBody @Validated AppMaterialModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return materialService.editDept(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/setType")
    @SystemLog(description = "设置材料类型", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:materialRaw:edit,dataentry:materialDataentry:edit,dataentry:materialReserve:edit")
    @ApiOperation(value = "设置材料类型", notes = "设置材料类型", response = boolean.class)
    public boolean setType(@RequestBody @Validated AppMaterialModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return materialService.setType(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/del")
    @SystemLog(description = "删除物料", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:materialRaw:del,dataentry:materialDataentry:del,dataentry:materialReserve:del")
    @ApiOperation(value = "删除物料", notes = "删除物料", response = boolean.class)
    public boolean delMaterial(@RequestBody @Validated MaterialEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return materialService.delMaterial(userInfo.getCompanyId(), model);
    }

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
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<MaterialExcel> excelResult = excelService.getExcelResult(tempPath, file, MaterialExcel.class, tableField);
        ImportMaterialModel importMaterialModel = materialService.importMaterial(info, excelResult.getData(), tableField.getType(), tableField.getYear(), isRD);
        return importMaterialModel;
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
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<MaterialExcel> excelResult = excelService.getExcelResult(tempPath, file, MaterialExcel.class, tableField);
        materialService.confirmImport(info, excelResult.getData(), tableField.getType(), tableField.getYear(), isRD);
        return excelResult.getMsg();
    }

    /**
     * @param model
     * @return
     * @throws Exception
     */
    @SystemLog(description = "导出物料清单")
    @GetMapping("/exportMaterial")
    @PermissionRequired(perms = "dataentry:materialRaw:export,dataentry:materialDataentry:export,dataentry:materialReserve:export")
    @ApiOperation(value = "导出物料清单 ", notes = "导出物料清单")
    public List<MaterialExcel> exportMaterial(AppMaterialModel model) throws Exception {
        UserInfo userInfo = getUserInfo();
        model.setCompanyId(userInfo.getCompanyId());
        return materialService.exportMaterialModel(model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delSelect")
    @SystemLog(description = "批量删除物料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除物料", notes = "批量删除物料", response = boolean.class)
    @PermissionRequired(perms = "dataentry:materialRaw:del,dataentry:materialDataentry:del,dataentry:materialReserve:del")
    public boolean delSelect(@RequestBody @Validated AppMaterialModel model) throws OwnerException {
        return materialService.delSelect(model.getIds());
    }

    @GetMapping("/getMaterialPlan")
    @ApiOperation(value = "获取用料计划表数据", notes = "获取用料计划表数据", response = String.class)
    public PageModel<List<MaterialPlanModel>> getMaterialPlan(QueryMaterialTrackModel model) throws OwnerException {
        return materialService.getMaterialPlan(model);
    }

    @PostMapping("/setPicker")
    @ApiOperation(value = "设置领料信息", notes = "设置领料信息")
    @SystemLog(description = "设置领料信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:materialRaw:setPicker,dataentry:materialDataentry:setPicker,dataentry:materialReserve:setPicker")
    public Boolean setPicker(@RequestBody @Validated SetMaterialPickerModel setPicker) throws OwnerException {
        return materialService.setPicker(setPicker, getUserInfo());
    }

}
