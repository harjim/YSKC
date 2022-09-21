package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignModel;
import com.yskc.rs.models.design.DesignQuery;
import com.yskc.rs.models.excel.DesginExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
import com.yskc.rs.service.DesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;


/**
 * 研发设计类接口
 *
 * @author huronghua
 */
@Api(tags = "研发设计类接口", value = "研发设计类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/design")
public class DesignController extends BaseController {
    @Autowired
    private DesignService designService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * 获取设计费用
     *
     * @param designQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDesignList")
    @PermissionRequired(perms = "dataentry:design:search")
    @ApiOperation(value = "获取设计费用", notes = "获取设计费用", response = List.class)
    public PageModel<List<DesignModel>> queryDesignList(DesignQuery designQuery) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return designService.queryDesignEntity(userInfo.getCompanyId(), designQuery);
    }

    /**
     * 删除设计费用
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delDesign")
    @SystemLog(description = "删除设计费用", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:design:del")
    @ApiOperation(value = "删除设计费用", notes = "删除设计费用", response = boolean.class)
    public boolean delDesign(@RequestBody @Validated DesignModel model) throws OwnerException {
        return designService.delDesign(model);
    }

    /**
     * 更新设计费用
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateDesign")
    @SystemLog(description = "更新设计费用", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:design:edit")
    @ApiOperation(value = "更新设计费用", notes = "更新设计费用", response = boolean.class)
    public boolean updateDesign(@RequestBody @Validated DesignModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return designService.updateDesign(userInfo, model);
    }

    /**
     * 添加设计费用
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加设计费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/addDesign")
    @PermissionRequired(perms = "dataentry:design:add")
    @ApiOperation(value = "添加设计费用", notes = "添加设计费用", response = boolean.class)
    public boolean addDesign(@RequestBody @Validated DesignModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return designService.addDesign(userInfo.getId(), userInfo, model);
    }

    /**
     * 导入设计费用
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importDesignData")
    @SystemLog(description = "导入设计费用", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:design:import")
    @ApiOperation(value = "导入设计费用", notes = "导入设计费用", response = String.class)
    public String importDesignData(@RequestParam("file") MultipartFile file, TableField tableField, Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<DesginExcel> excelResult = excelService.getExcelResult(tempPath, file, DesginExcel.class, tableField);
        designService.importDesign(userInfo, excelResult.getData(), year);
        return excelResult.getMsg();

    }

    /**
     * 导出研发设计
     *
     * @return
     * @throws Exception
     */
    @SystemLog(description = "导出研发设计")
    @GetMapping("/exportDesign")
    @PermissionRequired(perms = "dataentry:design:export")
    @ApiOperation(value = "导出研发设计 ", notes = "导出研发设计")
    public List<DesginExcel> exportDesign(DesignQuery designQuery) throws Exception {
        return designService.exportDesignModel(getUserInfo().getCompanyId(), designQuery);
    }

    @GetMapping("/queryDesignByTerm")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取设计费用", notes = "获取设计费用")
    public PageModel<List<DesignModel>> queryDesignByTerm(QueryProjectDesignModel query) throws OwnerException {
        return designService.queryDesignByTerm(getUserInfo().getCompanyId(), query);
    }

    /**
     * 批量删除设计
     *
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除设计", mode = SystemLog.SAVE_DB)
    @PostMapping("/delDesignBatch")
    @PermissionRequired(perms = "dataentry:design:del")
    @ApiOperation(value = "批量删除设计", notes = "批量删除设计", response = boolean.class)
    public boolean delDesignBatch(@RequestBody @Validated List<DesignModel> modelList) throws OwnerException {
        return designService.delDesignBatch(modelList);
    }
}
