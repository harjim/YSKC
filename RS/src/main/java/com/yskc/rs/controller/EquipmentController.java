package com.yskc.rs.controller;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.EquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:23
 * @Description: 设备控制层
 */

@Api(tags = "设备类接口", value = "设备类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @PermissionRequired(perms = "company:equipment:search")
    @ApiOperation(value = "查询设备", notes = "查询设备", response = PageModel.class)
    public PageModel<List<EquipmentModel>> queryAll(QueryEquipmentModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        return equipmentService.queryAll(info.getCompanyId(), query);
    }

    /**
     * @param ecode
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "检查ecode是否重复")
    @GetMapping("/checkEcode")
    @PermissionRequired(perms = "company:equipment:search")
    @ApiOperation(value = "检查ecode是否重复", notes = "检查ecode是否重复")
    public Boolean checkEcode(String ecode, Integer id) throws OwnerException {
        return equipmentService.checkEcode(getUserInfo().getCompanyId(), ecode, id);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addEquipment")
    @SystemLog(description = "添加设备", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:equipment:add")
    @ApiOperation(value = "添加设备", notes = "添加设备", response = Boolean.class)
    public Boolean modifyEquipment(@RequestBody @Validated EquipmentModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return equipmentService.addEquipment(info, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateEquipment")
    @SystemLog(description = "更新设备", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:equipment:edit")
    @ApiOperation(value = "更新设备", notes = "更新设备", response = Boolean.class)
    public Boolean updateEquipment(@RequestBody @Validated EquipmentModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return equipmentService.updateEquipment(info.getId(), info, model);
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除设备", mode = SystemLog.SAVE_DB)
    @GetMapping("/delEquipment")
    @PermissionRequired(perms = "company:equipment:del")
    @ApiOperation(value = "删除设备", notes = "删除设备", response = Boolean.class)
    public Boolean delEquipment(Integer id) throws OwnerException {
        return equipmentService.deleteById(getUserInfo().getCompanyId(), id);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/delEquipments")
    @PermissionRequired(perms = "company:equipment:del")
    @ApiOperation(value = "批量删除设备", notes = "批量删除设备")
    public Boolean delEquipments(@RequestBody @Validated EquipmentModifyModel model) throws OwnerException {
        return equipmentService.delEquipments(getUserInfo().getCompanyId(), model);
    }

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
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<EquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, EquipmentExcel.class, tableField);
        String error = equipmentService.importEquipment(userInfo, excelResult.getData());
        if (!StrUtil.isEmpty(error)) {
            return excelResult.msg + error;
        }
        return excelResult.msg;
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导出设备")
    @GetMapping("/exportEquipment")
    @PermissionRequired(perms = "company:equipment:export")
    @ApiOperation(value = "导出设备", notes = "导出设备")
    public List<EquipmentExcel> expertEquipment(QueryEquipmentModel query) throws OwnerException {
        return equipmentService.exportEquipment(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateEquipmentEtype")
    @SystemLog(description = "批量设置设备类型", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:equipment:setType")
    @ApiOperation(value = "批量设置类型", notes = "批量设置类型", response = boolean.class)
    public boolean updateEquipmentEtype(@RequestBody @Validated EquipmentModifyModel model) throws OwnerException {
        return equipmentService.updateEquipmentEtype(getUserInfo(), model);
    }

}
