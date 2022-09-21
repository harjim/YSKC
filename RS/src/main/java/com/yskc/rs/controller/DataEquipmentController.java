package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.data.DataEquipmentEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataEquipmentModel;
import com.yskc.rs.models.data.DataEquipmentModifyModel;
import com.yskc.rs.models.equipment.EquipmentMinModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.excel.DataEquipmentExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.service.DataEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * 设备使用类接口
 *
 * @author huronghua
 */
@Api(tags = "设备使用类接口", value = "设备使用类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/dEquipment")
public class DataEquipmentController extends BaseController {
    @Autowired
    private DataEquipmentService dequipmentService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * 获取设备使用
     *
     * @param queryEquipmentModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @PermissionRequired(perms = "dataentry:equipment:search")
    @ApiOperation(value = "获取设备使用", notes = "获取设备使用")
    public PageModel<List<DataEquipmentModel>> getList(QueryProjectEquipmentModel queryEquipmentModel) throws OwnerException {
        return dequipmentService.getDataEquitmentList(getUserInfo().getCompanyId(), queryEquipmentModel);
    }

    /**
     * 更新设备使用
     *
     * @param equipment
     * @return
     * @throws OwnerException
     */
    @PostMapping("/update")
    @PermissionRequired(perms = "dataentry:equipment:edit")
    @SystemLog(description = "更新设备使用",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新设备使用", notes = "更新设备使用")
    public boolean update(@RequestBody @Validated DataEquipmentModel equipment) throws OwnerException {
        return dequipmentService.update(equipment, getUserInfo());
    }

    /**
     * 添加设备使用
     *
     * @param equipmentEntity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加设备使用",mode = SystemLog.SAVE_DB)
    @PostMapping("/addEquipment")
    @PermissionRequired(perms = "dataentry:equipment:add")
    @ApiOperation(value = "添加设备使用", notes = "添加设备使用")
    public Boolean addEquipment(@RequestBody @Validated DataEquipmentEntity equipmentEntity) throws OwnerException {
        return dequipmentService.save(equipmentEntity, getUserInfo());
    }


    /**
     * 删除设备使用信息
     *
     * @param dequipment
     * @return
     * @throws OwnerException
     */
    @PostMapping("/del")
    @PermissionRequired(perms = "dataentry:equipment:del")
    @ApiOperation(value = "删除设备使用", notes = "删除设备使用")
    @SystemLog(description = "删除设备使用",mode = SystemLog.SAVE_DB)
    public boolean delete(@RequestBody @Validated DataEquipmentEntity dequipment) throws OwnerException {
        return dequipmentService.delete(getUserInfo().getCompanyId(), dequipment);
    }

    /**
     * 删除设备使用信息
     *
     * @param modifyModels
     * @return
     * @throws OwnerException
     */
    @PostMapping("/dels")
    @SystemLog(description = "批量删除设备使用",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:equipment:del")
    @ApiOperation(value = "批量删除设备使用", notes = "批量删除设备使用")
    public boolean dels(@RequestBody @Validated List<DataEquipmentModifyModel> modifyModels) throws OwnerException {
        return dequipmentService.dels(getUserInfo().getCompanyId(), modifyModels);
    }

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
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<DataEquipmentExcel> dataEquipmentExcels = excelService.getExcelResult(tempPath, file, DataEquipmentExcel.class, tableField);
        dequipmentService.importInfo(userInfo, dataEquipmentExcels.getData());
        return dataEquipmentExcels.msg;
    }

    /**
     * 设置运行工时
     *
     * @param modifyModels
     * @return
     * @throws OwnerException
     */
    @PostMapping("/setWorkHour")
    @PermissionRequired(perms = "dataentry:equipment:setHour")
    @SystemLog(description = "设置运行工时",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置运行工时", notes = "设置运行工时")
    public boolean setWorkHour(@RequestBody @Validated List<DataEquipmentModifyModel> modifyModels) throws OwnerException {
        return dequipmentService.setWorkHour(modifyModels, getUserInfo());
    }

    /**
     * 获取设备
     *
     * @param value
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEquipment")
    @PermissionRequired(perms = "dataentry:equipment:add")
    @ApiOperation(value = "获取设备", notes = "获取设备")
    public List<EquipmentMinModel> getEquipment(String value) throws OwnerException {
        return dequipmentService.getEquipment(value, getUserInfo().getCompanyId());
    }

    @PostMapping("/updateEtype")
    @SystemLog(description = "更新设备类型",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新设备类型", notes = "更新设备类型")
    @PermissionRequired(perms = "company:equipment:setType")
    public Boolean updateEtype(@RequestBody @Validated EquipmentModifyModel modifyModel) throws OwnerException {
        return dequipmentService.updateEtype(getUserInfo(), modifyModel);
    }


    /**
     * @param queryEquipmentModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/exportDataEquipment")
    @SystemLog(description = "导出设备使用",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出设备使用", notes = "导出设备使用")
    public List<DataEquipmentExcel> exportDataEquipment(QueryProjectEquipmentModel queryEquipmentModel) throws OwnerException {
        return dequipmentService.exportDataEquipment(getUserInfo(), queryEquipmentModel);
    }

}
