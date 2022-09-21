package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.ProjectInfoSummaryModel;
import com.yskc.rs.models.projectSummary.PolicySummaryModel;
import com.yskc.rs.models.projectSummary.SituationSummaryModel;
import com.yskc.rs.models.projectSummary.StandardModel;
import com.yskc.rs.models.trialprod.StageTrialModel;
import com.yskc.rs.service.SituationSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:41
 * @Description:
 */
@Api(tags = "项目情况总结接口", value = "项目情况总结接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/situationSummary")
public class SituationSummaryController extends BaseController {

    @Autowired
    private SituationSummaryService situationSummaryService;


    //@PermissionRequired(perms = "project:patentPlan:search")
    @GetMapping("/querySituation")
    @ApiOperation(value = "查询项目情况总结 ", notes = "查询项目情况总结")
    public SituationSummaryModel querySituation(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return situationSummaryService.querySituation(year, userInfo.getCompanyId());
    }


    @SystemLog(description = "保存项目情况总结", mode = SystemLog.SAVE_DB)
    // @PermissionRequired(perms = "project:patentPlan:add")
    @PostMapping("/saveSituation")
    @ApiOperation(value = "保存项目情况总结 ", notes = "保存项目情况总结")
    public Integer saveSituation(@RequestBody @Validated SituationSummaryModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return situationSummaryService.saveSituation(model, userInfo);
    }

    //@PermissionRequired(perms = "project:patentPlan:search")
    @GetMapping("/queryStandard")
    @ApiOperation(value = "查询标准化实施情况 ", notes = "查询标准化实施情况")
    public StandardModel queryStandard(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return situationSummaryService.queryStandard(year, userInfo.getCompanyId());
    }

    @SystemLog(description = "保存标准化实施情况", mode = SystemLog.SAVE_DB)
    // @PermissionRequired(perms = "project:patentPlan:add")
    @PostMapping("/saveStandard")
    @ApiOperation(value = "保存标准化实施情况 ", notes = "保存标准化实施情况")
    public Boolean saveStandard(@RequestBody @Validated StandardModel model) throws OwnerException {
        return situationSummaryService.saveStandardInfo(model, getUserInfo());
    }

    @GetMapping("/queryPolicy")
    @ApiOperation(value = "查询政策及要求情况汇总 ", notes = "查询政策及要求情况汇总")
    public PolicySummaryModel queryPolicy() {
        return situationSummaryService.queryPolicy();
    }

    @SystemLog(description = "保存政策及要求情况", mode = SystemLog.SAVE_DB)
    // @PermissionRequired(perms = "project:patentPlan:add")
    @PostMapping("/savePolicy")
    @ApiOperation(value = "保存政策及要求情况 ", notes = "保存政策及要求情况")
    public Boolean savePolicy(@RequestBody @Validated PolicySummaryModel model) throws OwnerException {
        return situationSummaryService.savePolicy(model, getUserInfo());
    }


    @GetMapping("/getProjectSummary")
    @ApiOperation(value = "获取项目信息汇总 ", notes = "获取项目信息汇总")
    public List<ProjectInfoSummaryModel> getProjectSummary(Integer year) throws OwnerException {
        return situationSummaryService.getSummaryInfo(year, getUserInfo().getCompanyId());
    }

    @GetMapping("/getBudgetCost")
    @ApiOperation(value = "获取项目决算信息 ", notes = "获取项目决算信息")
    public Map<String, Object> getBudgetCost(Integer projectId) {
        return situationSummaryService.getBudgetCost(projectId);
    }

    @GetMapping("/getBudgetInfo")
    @ApiOperation(value = "获取项目预算信息 ", notes = "获取项目预算信息")
    public Map<String, Map<String,Object>> getBudgetInfo(Integer projectId,Integer year) {
        return situationSummaryService.getBudgetInfo(projectId,year);
    }

    @GetMapping("/getSummaryInfo")
    @ApiOperation(value = "获取项目归集信息 ", notes = "获取项目归集信息")
    public Map<Integer, Map<Object, BigDecimal>> getSummaryInfo(Integer year) throws OwnerException {
        return situationSummaryService.getProjectSummary(year, getUserInfo().getCompanyId());
    }

    @GetMapping("/getStaffHour")
    @ApiOperation(value = "获取研发人员工时(按月) ", notes = "获取研发人员工时(按月)")
    public List<Map<String, Object>> getStaffHour(Integer year) throws Exception {
        return situationSummaryService.getStaffHour(year, getUserInfo().getCompanyId());
    }

    @GetMapping("/getEquipmentHour")
    @ApiOperation(value = "获取仪器设备工时 ", notes = "获取仪器设备工时")
    public List<Map<String, Object>> getEquipmentHour(Integer year) throws Exception {
        return situationSummaryService.getEquipmentHour(year, getUserInfo().getCompanyId());
    }

    @GetMapping("/getMaterialRaw")
    @ApiOperation(value = "获取原材料用料 ", notes = "获取原材料用料")
    public List<Map<String, Object>> getMaterialRaw(Integer year,Integer type) throws Exception {
        return situationSummaryService.getMaterialRaw(year, type,getUserInfo().getCompanyId());
    }

    @GetMapping("/getMaterialAuxiliary")
    @ApiOperation(value = "获取辅料 ", notes = "获取辅料")
    public List<Map<String, Object>> getMaterialAuxiliary(Integer year,Integer type) throws Exception {
        return situationSummaryService.getMaterialAuxiliary(year, type,getUserInfo().getCompanyId());
    }

    @GetMapping("/getMaterialReserve")
    @ApiOperation(value = "获取备品备件 ", notes = "获取备品备件")
    public List<Map<String, Object>> getMaterialReserve(Integer year,Integer type) throws Exception {
        return situationSummaryService.getMaterialReserve(year, type,getUserInfo().getCompanyId());
    }

    @GetMapping("/getYieldAmount")
    @ApiOperation(value = "获取排期表 ", notes = "获取排期表")
    public List<Map<String, Object>> getYieldAmount(Integer year) throws Exception {
        return situationSummaryService.getYieldAmount(year, getUserInfo().getCompanyId());
    }


    @GetMapping("/getYieldInfo")
    @ApiOperation(value = "获取排期表详情 ", notes = "获取排期表详情")
    public PageModel<List<StageTrialModel>> getYieldInfo(QueryMaterialTrackModel model) {
        return situationSummaryService.getYieldInfo(model);
    }

    @GetMapping("/getMaterials")
    @ApiOperation(value = "获取材料/备品/辅料数据", notes = "获取用料计划表数据", response = String.class)
    public PageModel<List<MaterialPlanModel>> getMaterials(QueryMaterialTrackModel model) {
        return situationSummaryService.getMaterials(model);
    }

    @GetMapping("/getMaterialSummary")
    @ApiOperation(value = "获取研发材料归集费用", notes = "获取研发材料归集费用")
    public List<Map<String,Object>> getMaterialSummary(int type,int year) throws OwnerException{
        return situationSummaryService.getMaterialSummary(type,year,getUserInfo().getCompanyId());
    }

}
