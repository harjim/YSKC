package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ProjectOutsourcingExcel;
import com.yskc.rs.models.outsourcing.ProjectOutsourcingModel;
import com.yskc.rs.models.outsourcing.SaveOutsourcingModel;
import com.yskc.rs.service.ProjectOutsourcingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:04
 * @Description: 项目委外费用接口
 */
@Api(tags = "项目委外费用接口", value = "项目委外费用接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/projectOutsourcing")
public class ProjectOutsourcingController extends BaseController {

    @Autowired
    private ProjectOutsourcingService projectOutsourcingService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getOutsourcingList")
    @PermissionRequired(perms = "project:outsourcing:view")
    @ApiOperation(value = "获取委外费用列表", notes = "获取委外费用列表")
    public List<ProjectOutsourcingModel> getOutsourcingList(Integer year, Integer type) throws OwnerException {
        return projectOutsourcingService.getOutsourcingList(year, type, getUserInfo().getCompanyId());
    }

    @PostMapping("/save")
    @PermissionRequired(perms = "project:outsourcing:save")
    @ApiOperation(value = "保存委外项目费用", notes = "保存委外项目费用")
    @SystemLog(description = "保存委外项目费用", mode = SystemLog.SAVE_DB)
    public Boolean save(@RequestBody @Validated SaveOutsourcingModel saveOutsourcing) throws OwnerException {
        return projectOutsourcingService.save(saveOutsourcing, getUserInfo());
    }

    @PostMapping("/importOutsourcing")
    @PermissionRequired(perms = "project:outsourcing:import")
    @ApiOperation(value = "导入委外项目费用", notes = "导入委外项目费用")
    @SystemLog(description = "导入委外项目费用", mode = SystemLog.SAVE_DB)
    public Boolean importOutsourcing(@RequestParam("file") MultipartFile file, TableField tableField, Integer type, Integer year) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectOutsourcingExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectOutsourcingExcel.class, tableField);
        return projectOutsourcingService.importOutsourcing(info, excelResult.getData(), type, year);
    }

    @GetMapping("/exportOutsourcing")
    @PermissionRequired(perms = "project:outsourcing:export")
    @ApiOperation(value = "导出委外项目费用", notes = "导出委外项目费用")
    public void exportOutsourcing(Integer type, Integer year) throws OwnerException {
        UserInfo info = getUserInfo();
        List<Map<String,Object>> list = projectOutsourcingService.getExportOutsourcing(year, type, info.getCompanyId());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        String title = info.getCompanyName() + year + (type == 0 ? "国内委托费用" : "国外委托费用");
        dataMap.put("title", title);
        dataMap.put("year", year);
        exportFileByTemplate(dataMap, Constant.TEMPLATE_DIR + "项目委外费用模板.xlsx",title+".xlsx");
    }

}
