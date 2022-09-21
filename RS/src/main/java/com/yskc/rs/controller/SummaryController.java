package com.yskc.rs.controller;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.data.CompanyCostEntity;
import com.yskc.rs.models.activity.ModifyModel;
import com.yskc.rs.models.project.RdSalaryDetailModel;
import com.yskc.rs.models.summary.RdSituationModel;
import com.yskc.rs.models.summary.TotalSummaryModel;
import com.yskc.rs.service.BudgetService;
import com.yskc.rs.service.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-29 14:47
 * @Description: 项目费用接口
 */
@Api(tags = "项目费用接口", value = "项目费用接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/summary")
public class SummaryController extends BaseController {

    @Autowired
    private SummaryService summaryService;
    @Autowired
    private BudgetService budgetService;

    /**
     * @param year
     * @return
     * @throws OwnerException
     */
    @ApiOperation(value = "研究开发项目情况", notes = "研究开发项目情况")
    @GetMapping("/rdSituation")
    @PermissionRequired(perms = "science:situation:view")
    public List<RdSituationModel> rdSituation(Integer year) throws OwnerException {
        return summaryService.rdSituation(getUserInfo().getCompanyId(), year);
    }

    /**
     * @param year
     * @return
     * @throws OwnerException
     */
    @ApiOperation(value = "研究开发项目活动情况", notes = "研究开发项目活动情况")
    @GetMapping("/rdActive")
    @PermissionRequired(perms = "science:active:view")
    public Map<String, Object> rdActive(Integer year) throws OwnerException {
        return summaryService.rdActive(getUserInfo().getCompanyId(), year);
    }

    /**
     * @param year
     * @throws IOException
     * @throws OwnerException
     */
    @ApiOperation(value = "导出研究开发项目情况", notes = "导出研究开发项目情况")
    @GetMapping("/exportSituation")
    @PermissionRequired(perms = "science:situation:export")
    public void exportSituation(Integer year) throws IOException, OwnerException {
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}企业研究开发项目情况{1}.xls", year, DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {

            summaryService.exportSituation(getUserInfo().getCompanyId(), year, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    /**
     * @param year
     * @throws IOException
     * @throws OwnerException
     */
    @ApiOperation(value = "导出研究开发项目活动情况", notes = "导出研究开发项目活动情况")
    @GetMapping("/exportActive")
    @PermissionRequired(perms = "science:active:export")
    public void exportActive(Integer year) throws IOException, OwnerException {
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}企业研究开发活动及相关情况{1}.xls", year, DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {
            summaryService.exportActive(getUserInfo().getCompanyId(), year, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }

    }

    /**
     * @param year
     * @return
     * @throws OwnerException
     */
    @ApiOperation(value = "获取研究开发项目活动情况", notes = "获取研究开发项目活动情况")
    @GetMapping("/getTotalSummaries")
    @PermissionRequired(perms = "project:costAnalysis:costRatio:search")
    public List<TotalSummaryModel> getTotalSummaries(Integer year) throws OwnerException {
        return budgetService.getSummaryBudget(getUserInfo().getCompanyId(), year);
    }

    /**
     * 保存公司成本
     *
     * @return
     */
    @SystemLog(description = "保存公司成本", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveCompanyCost")
    @ApiOperation(value = "保存公司成本", notes = "保存公司成本", response = boolean.class)
    public Boolean saveCompanyCost(@RequestBody @Validated CompanyCostEntity costEntity) throws OwnerException {
        return summaryService.saveCompanyCost(getUserInfo(), costEntity);
    }

    /**
     * 获取公司年度成本
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @ApiOperation(value = "获取公司年度成本", notes = "获取公司年度成本")
    @GetMapping("/getCompanyCostList")
    public List<CompanyCostEntity> getCompanyCostList(Integer year) throws OwnerException {
        return summaryService.getCompanyCostList(year, getUserInfo());
    }

    /**
     * @param rdSituationModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/saveProjectGovCost")
    @PermissionRequired(perms = "science:situation:edit")
    @SystemLog(description = "保存项目政府资金", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存项目政府资金", notes = "保存项目政府资金")
    public Boolean saveProjectGovCost(@RequestBody @Validated RdSituationModel rdSituationModel) throws OwnerException {
        return summaryService.saveProjectGovCost(getUserInfo(), rdSituationModel);
    }

    /**
     * @param modifyModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存项目研发情况", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveActivity")
    @PermissionRequired(perms = "science:active:edit")
    @ApiOperation(value = "保存项目研发情况", notes = "保存项目研发情况")
    public Boolean saveActivity(@RequestBody @Validated ModifyModel modifyModel) throws OwnerException {
        return summaryService.saveActivity(getUserInfo(), modifyModel);
    }

    @GetMapping("/getRdSalaryDetail")
    @PermissionRequired(perms = "project:rdSalaryDetail:search")
    @ApiOperation(value = "获取项目科目薪资明细", notes = "获取项目科目薪资明细")
    public List<RdSalaryDetailModel> getRdSalaryDetail(Date month) throws OwnerException {
        return summaryService.getRdSalaryDetail(month, getUserInfo());
    }

    @GetMapping("/getSummaryCostByYear")
    @ApiOperation(value = "根据年获取每月的研发费用统计", notes = "根据年获取每月的研发费用统计")
    public Map<String, Object> getSummaryCostByYear(Integer year) throws OwnerException {
        return summaryService.getSummaryCostByYear(year, getUserInfo().getCompanyId());
    }


    @GetMapping("/getProjectSummary")
    @ApiOperation(value = "获取项目的归集数据", notes = "获取项目的归集数据")
    @PermissionRequired(perms = "project:forFutureReferenceData:view")
    public List<Map<String,Object>> getProjectSummary(Integer projectId,Integer year) throws OwnerException{
        return summaryService.getProjectSummary(projectId,year,getUserInfo().getCompanyId());
    }

}
