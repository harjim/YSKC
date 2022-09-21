package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accountingrdsalary.AccountingRdSalaryModel;
import com.yskc.rs.models.aggregation.AggFeeModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectrdemployee.*;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.ProjectRdEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:06
 * @Description: 项目人员费用接口
 */
@RequestMapping("/api/projectRdEmployee")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@Api(tags = "项目人员费用接口", value = "项目人员费用接口")
@RestController
public class ProjectRdEmployeeController extends BaseController {

    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取项目人员费用列表", notes = "获取项目人员费用列表")
    public PageResult getList(QueryProjectRdEmployeeModel query) throws OwnerException {
        return projectRdEmployeeService.getList(getUserInfo(), query);
    }

    @SystemLog(description = "批量保存项目人员费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量保存项目人员费用", notes = "批量保存项目人员费用")
    public Boolean saveList(@RequestBody @Validated BatchProjectRdEmployeeModel batchModel) throws OwnerException {
        return projectRdEmployeeService.saveList(getUserInfo(), batchModel);
    }

    @GetMapping("/getListByMonth")
    @ApiOperation(value = "根据月份获取项目人员列表", notes = "根据月份获取项目人员列表")
    public PageModel<List<RdEmployeeSummaryModel>> getListByMonth(QueryProjectRdEmployeeModel model) throws OwnerException {
        return projectRdEmployeeService.queryListByMonth(getUserInfo(), model);
    }

    @GetMapping("/getMonthTotalStaff")
    @ApiOperation(value = "根据月份获取所有人员工时", notes = "根据月份获取所有人员工时")
    public PageModel<List<RdEmployeeSummaryModel>> getMonthTotalStaff(QueryProjectRdEmployeeModel model) throws OwnerException {
        return projectRdEmployeeService.getMonthTotalStaff(getUserInfo(), model);
    }

    @GetMapping("/getListByYear")
    @ApiOperation(value = "根据年份获取项目人员列表", notes = "根据年获取项目人员列表")
    public List<RdEmployeeSummaryModel> getListByYear(QueryProjectRdEmployeeModel model) throws OwnerException {
        return projectRdEmployeeService.queryListByYear(getUserInfo(), model);
    }

    @SystemLog(description = "导入人员研发工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdHour")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入人员研发工时", notes = "导入人员研发工时")
    public Boolean importRdHour(@RequestParam("file") MultipartFile file, Integer projectId, Integer year, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectRdEmployeeModel> excelResult = excelService.getExcelResult(tempPath, file, ProjectRdEmployeeModel.class, tableField);
        BatchProjectRdEmployeeModel batchModel = new BatchProjectRdEmployeeModel();
        batchModel.setProjectId(projectId);
        batchModel.setList(excelResult.getData());
        return projectRdEmployeeService.importRdHour(info, batchModel, year);
    }

    @GetMapping("/getRdAccountingList")
    @PermissionRequired(perms = "project:rdSalaryAccounting:view")
    @ApiOperation(value = "获取研发工资核算列表", notes = "获取研发工资核算列表")
    public PageResult getRdAccountingList(QueryProjectRdEmployeeModel query) throws OwnerException {
        return projectRdEmployeeService.getRdAccountingList(getUserInfo(), query);
    }

    @SystemLog(description = "核算研发工资", mode = SystemLog.SAVE_DB)
    @PostMapping("/accountingRdSalary")
    @PermissionRequired(perms = "project:rdSalaryAccounting:agg")
    @ApiOperation(value = "核算研发工资", notes = "核算研发工资")
    public Boolean accountingRdSalary(@RequestBody AccountingRdSalaryModel accountingRdSalary) throws OwnerException {
        return projectRdEmployeeService.accountingRdSalary(getUserInfo(), accountingRdSalary);
    }


    @SystemLog(description = "核算项目月份研发工资", mode = SystemLog.SAVE_DB)
    @PostMapping("/accountingAllRdSalary")
    @PermissionRequired(perms = "project:rdSalaryAccounting:agg")
    @ApiOperation(value = "核算项目月份研发工资", notes = "核算项目月份研发工资")
    public Boolean accountingAllRdSalary(@RequestBody AccountingRdSalaryModel accountingRdSalary) throws OwnerException {
        return projectRdEmployeeService.accountingAllRdSalary(getUserInfo(), accountingRdSalary);
    }

    @GetMapping("/getRdEmployeeList")
//    @PermissionRequired(perms = "project:batchRdEmployeeAgg:search")
    @ApiOperation(value = "获取研发花名册及关联的项目列表", notes = "获取研发花名册及关联的项目列表")
    public PageModel<List<RdEmployeeAggModel>> getRdEmployeeProjectList(QueryProjectRdEmployeeModel query) throws OwnerException {
        return projectRdEmployeeService.getRdEmployeeRdsList(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getRdAggConfig")
    @ApiOperation(value = "获取研发归集配置", notes = "获取研发归集配置")
    public String getRdAggConfig(Integer type, Date month) throws OwnerException {
        return projectRdEmployeeService.getRdAggConfig(type, month, getUserInfo().getCompanyId());
    }

    @SystemLog(description = "通过配置归集人员费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/aggFee")
    @ApiOperation(value = "通过配置归集人员费用", notes = "通过配置归集人员费用")
    public Boolean aggFee(@RequestBody @Validated AggFeeModel aggFee) throws OwnerException {
        return projectRdEmployeeService.aggFee(aggFee, getUserInfo());
    }

    @SystemLog(description = "刷新人员归集费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/refreshFee")
    @ApiOperation(value = "刷新人员归集费用", notes = "刷新人员归集费用")
    public Boolean refreshFee(@RequestBody @Validated AggFeeModel aggFee) throws OwnerException {
        return projectRdEmployeeService.refreshFee(aggFee, getUserInfo());
    }

    @SystemLog(description = "计划工时归集人员类型费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/aggRdPlanFee")
    @ApiOperation(value = "计划工时归集人员类型费用", notes = "计划工时归集人员类型费用")
    @PermissionRequired(perms = "project:rdEmployeePlan:agg")
    public Boolean aggRdPlanFee(@RequestBody @Validated AggFeeModel aggFee) throws OwnerException {
        return projectRdEmployeeService.aggRdPlanFee(aggFee, getUserInfo());
    }

    @SystemLog(description = "获取研发工资或五险一金明细")
    @GetMapping("/getFeeDetail")
    @ApiOperation(value="获取研发工资或五险一金明细",notes = "获取研发工资或五险一金明细")
    public List<SalaryRdFeeInfoModel> getFeeDetail(QueryProjectRdEmployeeModel query){
     return projectRdEmployeeService.getFeeDetail(query);
    }

}
