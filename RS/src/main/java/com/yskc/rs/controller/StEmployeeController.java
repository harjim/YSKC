package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.StEmployeeExcel;
import com.yskc.rs.models.rdemployeehour.QueryRdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourModel;
import com.yskc.rs.models.stEmployee.QueryStEmployeeModel;
import com.yskc.rs.models.stEmployee.StEmployeeModel;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.RdEmployeeService;
import com.yskc.rs.service.StEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * @CreateTime: 2022-03-08 08:58
 * @Description: 科技管理人员接口
 */
@Api(tags = "科技管理人员接口", value = "科技管理人员接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RequestMapping("/api/stEmployee")
@RestController
public class StEmployeeController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StEmployeeService stEmployeeService;

    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private ExcelService excelService;


    @GetMapping("/getList")
    @PermissionRequired(perms = "rdorg:stEmployee:search")
    @ApiOperation(notes = "获取科技管理人员列表", value = "获取科技管理人员列表")
    public PageModel<List<StEmployeeModel>> getList(QueryStEmployeeModel query) throws OwnerException {
        return stEmployeeService.getList(query,getUserInfo());
    }

    @PermissionRequired(perms = "rdorg:stEmployee:add")
    @SystemLog(description = "添加科技管理人员", mode = SystemLog.SAVE_DB)
    @PostMapping("/addStEmployee")
    @ApiOperation(value = "添加科技管理人员", notes = "添加科技管理人员", response = boolean.class)
    public boolean addStEmployee(@RequestBody @Validated StEmployeeModel model)throws OwnerException{
        return stEmployeeService.addStEmployee(model,getUserInfo());
    }

    @PermissionRequired(perms = "rdorg:stEmployee:del")
    @SystemLog(description = "批量删除科技管理人员", mode = SystemLog.SAVE_DB)
    @PostMapping("/delStEmployee")
    @ApiOperation(value = "批量删除科技管理人员", notes = "批量删除科技管理人员", response = boolean.class)
    public boolean delStEmployee(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return stEmployeeService.delStEmployee(deleteModel);
    }

    @GetMapping("/export")
    @PermissionRequired(perms = "rdorg:stEmployee:export")
    @SystemLog(description = "导出研发花名册", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "导出研发花名册",value = "导出研发花名册")
    public void export(QueryStEmployeeModel query)throws OwnerException{
        UserInfo info = getUserInfo();
        String templateName = "科技管理人员花名册模板.xlsx";
        String fileName = "科技管理人员花名册.xlsx";
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            stEmployeeService.exportStEmployee(info.getCompanyId(), query,out,path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

    @SystemLog(description = "导入研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importStEmployee")
    @PermissionRequired(perms = "rdorg:stEmployee:import")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importStEmployee(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<StEmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, StEmployeeExcel.class, tableField);
        String msg = excelResult.msg;
        if (StringUtils.hasLength(msg)){
            return msg;
        }else {
            return stEmployeeService.importStEmployee(info, excelResult.getData(), year);
        }
    }

}
