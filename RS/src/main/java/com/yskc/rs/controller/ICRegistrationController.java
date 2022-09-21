package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.ICRegistrationModel;
import com.yskc.rs.models.QueryICRegistrationModel;
import com.yskc.rs.models.SoftRegistrationModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ICRegistrationExcel;
import com.yskc.rs.models.excel.PatentDetailExcel;
import com.yskc.rs.models.excel.SoftRegistrationExcel;
import com.yskc.rs.service.ICRegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @DateTime: 2021/11/2 14:24
 * @Description:集成电路设计类接口
 * @author: hsx
 */
@Api(tags = "集成电路设计类接口", value = "集成电路设计类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/ICRegistration")
public class ICRegistrationController extends BaseController{
    @Autowired
    private ICRegistrationService iCRegistrationService;

    @Autowired
    private RsConfig rsConfig;

    @SystemLog(description = "导入集成电路设计列表",mode = SystemLog.SAVE_DB)
    @PostMapping("/import")
    @PermissionRequired(perms = "project:ICRegistration:import")
    @ApiOperation(value = "导入集成电路设计列表", notes = "导入集成电路设计列表", response = Boolean.class)
    public Boolean importICRegistration(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ICRegistrationExcel> excelResult= excelService.getExcelResult(tempPath, file, ICRegistrationExcel.class, tableField);
        return iCRegistrationService.importICRegistration(info, excelResult.getData());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:ICRegistration:search")
    @ApiOperation(value = "查询集成电路设计列表", notes = "查询集成电路设计列表")
    public PageModel<List<ICRegistrationModel>> getList(QueryICRegistrationModel model) throws OwnerException{
        return iCRegistrationService.getList(model,getUserInfo().getCompanyId());
    }

    @SystemLog(description = "新增集成电路设计列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:ICRegistration:add")
    @ApiOperation(value = "新增集成电路设计列表", notes = "新增集成电路设计列表", response = Boolean.class)
    public Boolean add(@RequestBody ICRegistrationModel model) throws OwnerException{
        return iCRegistrationService.add(model, getUserInfo());
    }

    @SystemLog(description = "编辑集成电路设计列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:ICRegistration:edit")
    @ApiOperation(value = "编辑集成电路设计列表", notes = "编辑集成电路设计列表", response = Boolean.class)
    public Boolean edit(@RequestBody ICRegistrationModel model) throws OwnerException{
        return iCRegistrationService.edit(model,getUserInfo());
    }

    @SystemLog(description = "删除集成电路设计列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:ICRegistration:del")
    @ApiOperation(value = "删除集成电路设计列表", notes = "删除集成电路设计列表", response = Boolean.class)
    public Boolean del(@RequestBody List<Integer> ids) throws OwnerException{
        return iCRegistrationService.del(ids);
    }

    @GetMapping("/getText")
    @ApiOperation(value = "获取说明书内容", notes = "获取说明书内容", response = SoftRegistrationModel.class)
    public ICRegistrationModel getText(@RequestParam("id") Integer id) {
        return iCRegistrationService.getText(id);
    }
}
