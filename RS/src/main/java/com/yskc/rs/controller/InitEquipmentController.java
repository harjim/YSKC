package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.InitEquipmentExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.equipment.BatchInitEquipmentModel;
import com.yskc.rs.models.init.equipment.InitEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.InitEquipmentService;
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
 * @CreateTime: 2019-07-23 17:09
 * @Description: 设备清单controller层
 */
@Api(tags = "设备清单接口", value = "设备清单接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/initEquipment")
public class InitEquipmentController extends BaseController {

    @Autowired
    private InitEquipmentService initEquipmentService;

    @Autowired
    private RsConfig rsConfig;

    @Autowired
    private ExcelService excelService;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @PermissionRequired(perms = "project:report:equipment:search")
    @ApiOperation(value = "获取设备清单列表", notes = "获取设备清单列表")
    public PageModel<List<InitEquipmentModel>> getList(QueryProjectEquipmentModel query) throws OwnerException {
        return initEquipmentService.getList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEquipmentList")
    @PermissionRequired(perms = "project:report:equipment:add")
    @ApiOperation(value = "获取非当前项目设备列表", notes = "获取非当前项目设备列表")
    public PageModel<List<InitEquipmentModel>> getEquipmentList(QueryProjectEquipmentModel model) throws OwnerException {
        return initEquipmentService.getEquipmentList(getUserInfo().getCompanyId(), model);
    }

    /**
     * @param batchInitEquipmentModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量添加设备清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/addList")
    @PermissionRequired(perms = "project:report:equipment:add")
    @ApiOperation(value = "批量添加设备清单", notes = "批量添加设备清单")
    public boolean addList(@RequestBody @Validated BatchInitEquipmentModel batchInitEquipmentModel) throws OwnerException {
        if (null == batchInitEquipmentModel || null == batchInitEquipmentModel.getEcodes() || batchInitEquipmentModel.getEcodes().size() <= 0) {
            return false;
        }
        return initEquipmentService.addList(getUserInfo(), batchInitEquipmentModel);
    }

    /**
     * @param model
     * @return
     */
    @SystemLog(description = "删除项目设备清单", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:report:equipment:del")
    @ApiOperation(value = "删除项目设备清单", notes = "删除项目设备清单")
    public boolean del(@RequestBody @Validated InitEquipmentModel model) throws OwnerException {
        return initEquipmentService.del(model, getUserInfo());
    }

    @PostMapping("/updateEffect")
    @PermissionRequired(perms = "project:report:equipment:edit")
    @SystemLog(description = "更新项目设备作用", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新项目设备作用", notes = "更新项目设备作用")
    public boolean updateEffect(@RequestBody @Validated InitUsedRoleModel model) {
        return initEquipmentService.updateEffect(model);
    }

    @PostMapping("/importInitEquipment")
    @PermissionRequired(perms = "project:report:equipment:import")
    @SystemLog(description = "导入项目设备清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入项目设备清单", notes = "导入项目设备清单")
    public String importInitEquipment(@RequestParam("file") MultipartFile file, TableField tableField, int year, Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InitEquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, InitEquipmentExcel.class, tableField);
        initEquipmentService.importInitEquipment(userInfo, excelResult.getData(), year, projectId);
        return excelResult.msg;
    }

    @PostMapping("/delInitEquipments")
    @PermissionRequired(perms = "project:report:equipment:del")
    @SystemLog(description = "批量删除项目设备清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除项目设备清单", notes = "批量删除项目设备清单")
    public boolean delInitEquipments(@RequestBody @Validated InitUsedRoleModel model) throws OwnerException {
        return initEquipmentService.delInitEquipments(model, getUserInfo());
    }

    @PostMapping("/setEntryDate")
    @PermissionRequired(perms = "project:report:equipment:edit")
    @SystemLog(description = "设置项目设备加入日期", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置项目设备加入日期", notes = "设置项目设备加入日期")
    public boolean setEntryDate(@RequestBody @Validated InitUsedRoleModel model) throws OwnerException {
        return initEquipmentService.setEntryDate(getUserInfo(), model);
    }

    @SystemLog(description = "检查项目设备是否已经归集研发费用")
    @GetMapping("/checkRdUsed")
    @ApiOperation(value = "检查项目设备是否已经归集研发费用", notes = "检查项目设备是否已经归集研发费用")
    public String checkRdUsed(InitUsedRoleModel model) {
        return initEquipmentService.checkRdUsed(model);
    }

    @PostMapping("/importEquipments")
    @PermissionRequired(perms = "project:report:member:edit")
    @SystemLog(description = "根据年引入资产清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "根据年引入资产清单", notes = "根据年引入资产清单")
    public boolean importEquipments(@RequestBody @Validated ImportProjectInfoModel model) throws OwnerException {
        return initEquipmentService.importEquipments(getUserInfo(), model);
    }
    @SystemLog(description = "获取可引入项目设备的年份列表")
    @GetMapping("/getEquipmentsYear")
    @ApiOperation(value = "获取可引入项目设备的年份列表", notes = "获取可引入项目设备的年份列表")
    public List<Integer> getEquipmentsYear(@RequestParam(value = "year",required = true) Integer year,@RequestParam(value = "projectId",required = true)Integer projectId){
        return initEquipmentService.getEquipmentYear(year,projectId);
    }

    @SystemLog(description = "根据公司和年份获取设备信息")
    @GetMapping("/getEquList")
    @ApiOperation(value = "根据公司和年份获取设备信息", notes = "根据公司和年份获取设备信息")
    public List<InitEquipmentModel> getEquList(QueryProjectEquipmentModel model)throws OwnerException{
        return initEquipmentService.getEquList(getUserInfo().getCompanyId(),model);
    }
}
