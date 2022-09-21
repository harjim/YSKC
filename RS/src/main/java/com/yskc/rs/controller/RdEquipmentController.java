package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.RdEquipmentEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectequipment.DocEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdequipment.AddRdEquipmentModel;
import com.yskc.rs.models.rdequipment.RdEquipmentModel;
import com.yskc.rs.service.RdEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:11
 * @Description: 研发设备接口
 */
@Api(tags = "研发设备接口", value = "研发设备接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rdEquipment")
public class RdEquipmentController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RdEquipmentService rdEquipmentService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "rdorg:rdEquipment:search")
    @ApiOperation(notes = "获取研发设备", value = "获取研发设备")
    public PageModel<List<RdEquipmentModel>> getList(QueryEquipmentModel queryModel) throws OwnerException {
        return rdEquipmentService.getList(getUserInfo(), queryModel);
    }

    @GetMapping("/getEquipmentList")
    @ApiOperation(notes = "获取设备列表", value = "获取设备列表")
    public PageModel<List<EquipmentModel>> getEquipmentList(QueryEquipmentModel queryModel) throws OwnerException {
        return rdEquipmentService.getEquipmentList(getUserInfo(), queryModel);
    }

    @SystemLog(description = "删除研发设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @PermissionRequired(perms = "rdorg:rdEquipment:del")
    @ApiOperation(notes = "删除研发设备", value = "删除研发设备")
    public Boolean delete(@RequestBody @Validated RdEquipmentEntity rdEquipmentEntity) throws OwnerException {
        return rdEquipmentService.delete(getUserInfo(), rdEquipmentEntity);
    }

    @SystemLog(description = "批量删除研发设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/delRdEquipments")
    @PermissionRequired(perms = "rdorg:rdEquipment:del")
    @ApiOperation(notes = "批量删除研发设备", value = "批量删除研发设备")
    public Boolean delRdEquipments(@RequestBody BatchDeleteModel deleteModel) throws OwnerException {
        return rdEquipmentService.delRdEquipments(getUserInfo(), deleteModel);
    }

    @SystemLog(description = "批量添加研发设备", mode = SystemLog.SAVE_DB)
    @PostMapping("/addRdEquipments")
    @PermissionRequired(perms = "rdorg:rdEquipment:add")
    @ApiOperation(notes = "批量添加研发设备", value = "批量添加研发设备")
    public Boolean addRdEquipments(@RequestBody @Validated AddRdEquipmentModel addRdEquipmentModel) throws OwnerException {
        if (CollectionUtils.isEmpty(addRdEquipmentModel.getEcodes())) {
            throw new OwnerException("添加失败，未获取到任何设备。");
        }
        return rdEquipmentService.addRdEquipments(getUserInfo(), addRdEquipmentModel);
    }

    @SystemLog(description = "更新设备类型", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateEtype")
    @PermissionRequired(perms = "company:equipment:setType")
    @ApiOperation(notes = "更新设备类型", value = "更新设备类型")
    public Boolean updateEtype(@RequestBody @Validated EquipmentModifyModel modifyModel) throws OwnerException {
        return rdEquipmentService.updateEtype(getUserInfo(), modifyModel);
    }

    @SystemLog(description = "设置研发设备研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/setRdDept")
    @PermissionRequired(perms = "rdorg:rdEquipment:edit")
    @ApiOperation(notes = "设置研发设备研发部门", value = "设置研发设备研发部门")
    public Boolean setRdDept(@RequestBody @Validated EquipmentModifyModel modifyModel)throws OwnerException {
        return rdEquipmentService.setRdDept(modifyModel,getUserInfo());
    }

    /**
     * 导出研发设备
     *
     * @param queryModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/deriveRdEquipment")
    @SystemLog(description = "导出研发设备", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "rdorg:rdEquipment:export")
    @ApiOperation(value = "导出研发设备", notes = "导出研发设备")
    public void deriveRdEquipment(QueryEquipmentModel queryModel) throws OwnerException {
        String templateName = "研发设备列表模板.xls";
        String fileName = "研发设备列表数据.xls";
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            rdEquipmentService.exportRdEquipment(getUserInfo().getCompanyId(),queryModel,out,path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }


    /**
     * 获取项目设备列表
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getDocEquipmentList")
    @ApiOperation(value = "获取项目设备列表", notes = "获取项目设备列表")
    public PageModel<List<DocEquipmentModel>> getDocEquipmentList(QueryProjectEquipmentModel query) throws OwnerException {
        return rdEquipmentService.getDocEquipmentList(getUserInfo(), query);
    }

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
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<EquipmentExcel> excelResult = excelService.getExcelResult(tempPath, file, EquipmentExcel.class, tableField);
        return rdEquipmentService.importRdEquipment(info, excelResult.getData(), year);
    }

    @SystemLog(description = "分配研发设备至项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/setProjectEquipment")
    @PermissionRequired(perms = "rdorg:rdEquipment:setProject")
    @ApiOperation(value = "分配研发设备至项目", notes = "分配研发设备至项目")
    public Boolean setProjectEquipment(@RequestBody KeysAndIdsModel keysAndIds) throws OwnerException {
        return rdEquipmentService.setProjectEquipment(getUserInfo(), keysAndIds);
    }
}
