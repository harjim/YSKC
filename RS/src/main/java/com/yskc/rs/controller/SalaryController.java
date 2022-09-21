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
import com.yskc.rs.models.excel.SalaryExcel;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.salary.SalaryModel;
import com.yskc.rs.models.salary.SalaryQuery;
import com.yskc.rs.service.SalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;


/**
 * 员工薪资类接口
 *
 * @author huronghua
 */
@Api(tags = "员工薪资类接口", value = "员工薪资类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/salary")
public class SalaryController extends BaseController {

    @Autowired
    private SalaryService salaryService;
    @Autowired
    private RsConfig rsConfig;


    /**
     * @param salaryQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/querySalaryList")
    @PermissionRequired(perms = "dataentry:salary:search")
    @ApiOperation(value = "获取员工薪资", notes = "获取员工薪资", response = List.class)
    public PageModel<List<SalaryModel>> querySalaryList(SalaryQuery salaryQuery) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        salaryQuery.setCompanyId(userInfo.getCompanyId());
        return salaryService.querySalary(salaryQuery);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/delSalary")
    @PermissionRequired(perms = "dataentry:salary:del")
    @ApiOperation(value = "删除员工薪资", notes = "删除员工薪资", response = boolean.class)
    public boolean delSalary(@RequestBody @Validated SalaryModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return salaryService.delSalary(userInfo.getCompanyId(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateSalary")
    @PermissionRequired(perms = "dataentry:salary:edit")
    @ApiOperation(value = "更新员工薪资", notes = "更新员工薪资", response = boolean.class)
    public boolean updateSalary(@RequestBody @Validated SalaryModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return salaryService.updateSalary(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/addSalary")
    @PermissionRequired(perms = "dataentry:salary:add")
    @ApiOperation(value = "添加员工薪资", notes = "添加员工薪资", response = boolean.class)
    public boolean addSalary(@RequestBody @Validated SalaryModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return salaryService.addSalary(userInfo, model);
    }

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/importSalary")
    @PermissionRequired(perms = "dataentry:salary:import")
    @ApiOperation(value = "导入员工薪资", notes = "导入员工薪资", response = String.class)
    public String importSalary(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<SalaryExcel> excelResult = excelService.getExcelResult(tempPath, file, SalaryExcel.class, tableField);
        salaryService.importSalary(info, excelResult.getData());
        return excelResult.getMsg();
    }

    /**
     * @return
     * @throws Exception
     */
    @SystemLog(description = "导出薪资")
    @GetMapping("/salaryExport")
    @PermissionRequired(perms = "dataentry:salary:export")
    @ApiOperation(value = "导出薪资 ", notes = "导出薪资")
    public List<SalaryExcel> salaryExport(SalaryQuery salaryQuery) throws Exception {
        return salaryService.exportSalaryModel(getUserInfo().getCompanyId(), salaryQuery);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置日工时")
    @PostMapping("/updateSalaryDayHours")
    @ApiOperation(value = "批量设置日工时", notes = "批量设置日工时", response = boolean.class)
    public boolean updateSalaryDayHours(@RequestBody @Validated SalaryModel model) throws OwnerException {
        return salaryService.updateSalaryDayHours(getUserInfo(), model);
    }

    /**
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除员工薪资", mode = SystemLog.SAVE_DB)
    @PostMapping("/delSalaryBatch")
    @PermissionRequired(perms = "dataentry:salary:del")
    @ApiOperation(value = "批量删除员工薪资", notes = "批量删除员工薪资", response = boolean.class)
    public boolean delSalaryBatch(@RequestBody @Validated List<SalaryModel> modelList) throws OwnerException {
        return salaryService.delSalaryBatch(getUserInfo().getCompanyId(), modelList);
    }

    @GetMapping("/getSalaryConfig")
    @ApiOperation(value = "获取薪资配置", notes = "获取薪资配置")
    public Map<String, FieldConfigModel> getSalaryConfig() throws OwnerException {
        return salaryService.getSalaryConfig(getUserInfo().getCompanyId());
    }

}
