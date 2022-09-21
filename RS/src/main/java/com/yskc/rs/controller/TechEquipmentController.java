package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.tech.BeianInfoModel;
import com.yskc.rs.models.tech.QueryEquipmentModel;
import com.yskc.rs.models.tech.TechEquipmentModel;
import com.yskc.rs.service.BeianService;
import com.yskc.rs.service.TechEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:08
 * @Description:备案清单
 */
@Api(tags = "技改资料清单接口", value = "技改资料清单接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/techEquipment")
public class TechEquipmentController extends BaseController {

    @Autowired
    private TechEquipmentService techEquipmentService;
    @Autowired
    private BeianService beianService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "tech:beian:beianList:search")
    @ApiOperation(notes = "获取备案清单", value = "获取备案清单")
    public PageModel<List<TechEquipmentModel>> getList(QueryEquipmentModel query) throws OwnerException {
        return techEquipmentService.getList(getUserInfo().getCompanyId(), query);
    }

    @PostMapping("/edit")
    @PermissionRequired(perms = "tech:beian:beianList:edit")
    @SystemLog(description = "编辑备案清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "编辑备案清单", value = "编辑备案清单")
    public Boolean edit(@RequestBody @Validated TechEquipmentModel model) throws OwnerException {
        return techEquipmentService.edit(getUserInfo(), model);
    }

    @PostMapping("/deletes")
    @PermissionRequired(perms = "tech:beian:beianList:del")
    @SystemLog(description = "删除备案清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除备案清单", value = "删除备案清单")
    public Boolean deletes(@RequestBody @Validated BatchDeleteModel deleteModel) {
        return techEquipmentService.deletes(deleteModel);
    }

    @PostMapping("/add")
    @PermissionRequired(perms = "tech:beian:beianList:add")
    @SystemLog(description = "添加备案清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "添加备案清单", value = "添加备案清单")
    public Boolean add(@RequestBody @Validated TechEquipmentModel model) throws OwnerException {
        return techEquipmentService.add(model, getUserInfo());
    }

    @PostMapping("/importEquipment")
    @PermissionRequired(perms = "tech:beian:beianList:import")
    @SystemLog(description = "导入备案清单", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "导入备案清单", value = "导入备案清单")
    public String importEquipment(@RequestParam("file") MultipartFile file, TableField tableField, Integer beianId) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<TechEquipmentModel> excelResult = excelService.getExcelResult(tempPath, file, TechEquipmentModel.class, tableField);
        techEquipmentService.importEquipment(info, excelResult.getData(), beianId);
        return excelResult.getMsg();
    }

    @SystemLog(description = "导出备案清单", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportEquipment")
    @PermissionRequired(perms = "tech:beian:beianList:export")
    @ApiOperation(value = "导出备案清单", notes = "导出备案清单")
    public void exportPlan(Integer beianId) throws IOException, OwnerException {
        UserInfo info = getUserInfo();
        BeianInfoModel beian=beianService.getBeianInfo(info.getCompanyId(),beianId);
        String fileName=MessageFormat.format("{0}{1}备案清单.xls", info.getCompanyName(),beian.getPname());
        try (OutputStream out = outGeneralFile(fileName)) {
            techEquipmentService.exportEquipment(beianId,info, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }

    }
}
