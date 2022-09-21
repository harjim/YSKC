package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.*;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.PatentDetailExcel;
import com.yskc.rs.models.excel.SoftRegistrationExcel;
import com.yskc.rs.service.ICRegistrationService;
import com.yskc.rs.service.SoftRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @DateTime: 2021/11/2 14:25
 * @Description:计算机软件著作类接口
 * @author: hsx
 */
@Api(tags = "计算机软件著作类接口", value = "计算机软件著作类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/softRegistration")
public class SoftRegistrationController extends BaseController{

    @Autowired
    private SoftRegistrationService softRegistrationService;

    @Autowired
    private RsConfig rsConfig;

    @SystemLog(description = "导入计算机软件著作列表",mode = SystemLog.SAVE_DB)
    @PostMapping("/import")
    @PermissionRequired(perms = "project:softRegistration:import")
    @ApiOperation(value = "导入计算机软件著作列表", notes = "导入计算机软件著作列表", response = String.class)
    public Boolean importSoftRegistration(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<SoftRegistrationExcel> excelResult= excelService.getExcelResult(tempPath, file, SoftRegistrationExcel.class, tableField);
        return softRegistrationService.importSoftRegistration(info, excelResult.getData());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:softRegistration:search")
    @ApiOperation(value = "查询计算机软件著作列表", notes = "查询计算机软件著作列表")
    public PageModel<List<SoftRegistrationModel>> getList(QuerySoftRegistrationModel model) throws OwnerException{
        return softRegistrationService.getList(model,getUserInfo().getCompanyId());
    }

    @SystemLog(description = "新增集成电路设计列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:softRegistration:add")
    @ApiOperation(value = "新增计算机软件著作列表", notes = "新增计算机软件著作列表", response = Boolean.class)
    public Boolean add(@RequestBody SoftRegistrationModel model) throws OwnerException{
        return softRegistrationService.add(model, getUserInfo());
    }

    @SystemLog(description = "编辑计算机软件著作列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:softRegistration:edit")
    @ApiOperation(value = "编辑计算机软件著作列表", notes = "编辑计算机软件著作列表", response = Boolean.class)
    public Boolean edit(@RequestBody SoftRegistrationModel model) throws OwnerException{
        return softRegistrationService.edit(model,getUserInfo());
    }

    @SystemLog(description = "删除计算机软件著作列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:softRegistration:del")
    @ApiOperation(value = "删除计算机软件著作列表", notes = "删除计算机软件著作列表", response = Boolean.class)
    public Boolean del(@RequestBody List<Integer> ids) throws OwnerException{
        return softRegistrationService.del(ids);
    }

    @GetMapping("/getText")
    @ApiOperation(value = "获取说明书内容", notes = "获取说明书内容", response = SoftRegistrationModel.class)
    public SoftRegistrationModel getText(@RequestParam("id") Integer id) {
        return softRegistrationService.getText(id);
    }
}
