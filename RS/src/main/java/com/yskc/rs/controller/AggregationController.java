package com.yskc.rs.controller;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.summary.SimpleSummaryModel;
import com.yskc.rs.service.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @CreateTime: 2019-07-19 14:40
 * @Description: 费用汇总controller
 */
@Api(value = "费用汇总接口", tags = "费用汇总接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/aggregation")
public class AggregationController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SummaryService summaryService;


    /**
     * 项目汇总费用列表
     *
     * @param projectId
     * @return
     */
    @GetMapping("/getRdFunds")
    @ApiOperation(value = "项目汇总费用列表", notes = "项目汇总费用列表")
    public Map<String, Object> getRdFunds(Integer projectId, Integer year) {
        return summaryService.getRdFundsByProjectId(projectId, year);
    }

    /**
     * 所有RD的月数据
     *
     * @param rdYear
     * @param rdMonth
     * @param rdTypes
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getRdSummaryByMonth")
    @ApiOperation(value = "所有RD的月数据", notes = "所有RD的月数据")
    public List<SimpleSummaryModel> getRdSummaryByMonth(Integer rdYear, Date rdMonth, int[] rdTypes, Integer projectId) throws OwnerException {
        return summaryService.getRdSummaryByMonth(this.getUserInfo().getCompanyId(), rdYear, rdMonth, rdTypes, projectId);
    }

    @GetMapping("/exportFunds")
    @ApiOperation(value = "导出研发费用", notes = "导出研发费用")
    @PermissionRequired(perms = "project:data:export")
    @SystemLog(description = "导出研发费用")
    public void exportFunds(ExportTermModel exportTerm) throws OwnerException {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}研发费用{1}.xls", info.getCompanyName(), DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {
            summaryService.exportFunds(exportTerm, info, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    /**
     * @param year
     * @param exportType 1:按项目分sheet 2：按费用类型分sheet
     * @throws IOException
     * @throws OwnerException
     */
    @SystemLog(description = "导出归集明细表(按项目/费用类型)", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportDataDetail")
    @PermissionRequired(perms = "project:subsidiaryLedger:projectExport,project:subsidiaryLedger:typeExport")
    @ApiOperation(value = "导出归集明细表(按项目/费用类型)", notes = "导出归集明细表(按项目/费用类型)")
    public void exportDataDetail(Integer year, Integer exportType) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}年{1}研发费汇总表.xls", year, userInfo.getCompanyName()))) {
            summaryService.exportDataDetail(year, getUserInfo(), exportType, out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    /**
     * @param year
     * @param exportType 1:按项目分sheet 2：按费用类型分sheet
     * @throws IOException
     * @throws OwnerException
     */
    @SystemLog(description = "获取归集明细表(按项目/费用类型)数据", mode = SystemLog.SAVE_DB)
    @GetMapping("/getDataDetail")
    @PermissionRequired(perms = "project:RdFeeSummary:search,project:RdTypeFeeSummary:search")
    @ApiOperation(value = "获取归集明细表(按项目/费用类型)数据", notes = "获取归集明细表(按项目/费用类型)数据")
    public List<Map<String, Object>> getDataDetail(Integer year, Integer exportType) throws OwnerException {
        return summaryService.getDataDetail(year, getUserInfo(), exportType);
    }

    @SystemLog(description = "导出归集明细表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportSubsidiaryLedger")
    @PermissionRequired(perms = "project:subsidiaryLedger:export")
    @ApiOperation(value = "导出归集明细表", notes = "导出归集明细表")
    public void exportSubsidiaryLedger(Integer year) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}年{1}归集明细表.xls", year, userInfo.getCompanyName()))) {
            summaryService.exportSubsidiaryLedger(year, getUserInfo(), out,0);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    @SystemLog(description = "导出研发工资月报表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportRdSalaryReport")
    @PermissionRequired(perms = "project:rdSalaryRepot:export")
    @ApiOperation(value = "导出研发工资月报表", notes = "导出研发工资月报表")
    public void exportRdSalaryReport(Integer year,Integer type) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}年{1}研发工资月报表.xls", year, userInfo.getCompanyName()))) {
            summaryService.exportSubsidiaryLedger(year, getUserInfo(), out,type);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    @SystemLog(description = "导出归集总表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportSummaryReport")
    @PermissionRequired(perms = "project:summaryReport:export")
    @ApiOperation(value = "导出归集总表", notes = "导出归集总表")
    public void exportSummaryReport(Integer year) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}年{1}归集总表.xls", year, userInfo.getCompanyName()))) {
            summaryService.exportSummaryReport(year, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }


    @SystemLog(description = "导出研发薪资明细", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportRdSalaryDetail")
    @PermissionRequired(perms = "project:rdSalaryDetail:export")
    @ApiOperation(value = "导出研发薪资明细", notes = "导出研发薪资明细")
    public void exportRdSalaryDetail(Date month) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}研发薪资明细.xls", userInfo.getCompanyName()))) {
            summaryService.exportRdSalaryDetail(month, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }


    @SystemLog(description = "导出分页分RD", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportByRD")
    @PermissionRequired(perms = "project:summaryReport:export")
    @ApiOperation(value = "导出分页分RD", notes = "导出分页分RD")
    public void exportByRD(Integer projectId, Integer year) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}分页分RD.xls", userInfo.getCompanyName()))) {
            summaryService.exportByRD(projectId, year, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }


    @SystemLog(description = "导出研发月度汇总", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportByMonth")
    @PermissionRequired(perms = "project:summaryReport:export")
    @ApiOperation(value = "导出研发月度汇总", notes = "导出研发月度汇总")
    public void exportByMonth(Date month, Integer year) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}月度汇总.xls", userInfo.getCompanyName()))) {
            summaryService.exportByMonth(month, year, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

}
