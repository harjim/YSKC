package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.rdAgg.AggSummaryModel;
import com.yskc.rs.service.RdAggService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: rs
 * @description: 项目费用归集总表
 * @author: wjy
 * @create: 2022-06-17 13:44
 **/
@Api(tags = "项目费用归集总表接口", value = "项目费用归集总表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rdAgg")
public class RdAggController extends BaseController {
    @Autowired
    private RdAggService rdAggService;

    @SystemLog(description = "研发费用总表", mode = SystemLog.SAVE_DB)
    @GetMapping("/getDataReportFunds")
    @PermissionRequired(perms = "rdagg:aggregation:view")
    @ApiOperation(value = "研发费用总表", notes = "研发费用总表")
    public Map<String, Object> getDataReportFunds(Integer year) throws OwnerException {

        return rdAggService.getDataReportFunds(getUserInfo().getCompanyId(), year);
    }

    @SystemLog(description = "项目归集数据", mode = SystemLog.SAVE_DB)
    @GetMapping("/getProjectData")
    @PermissionRequired(perms = "rdagg:aggregation:view")
    @ApiOperation(value = "项目归集数据", notes = "项目归集数据")
    public Map<String, Object> getProjectData(Integer year,Integer projectId) throws OwnerException {

        return rdAggService.getProjectDataReport(year,projectId);
    }

    @SystemLog(description = "费用月度数据", mode = SystemLog.SAVE_DB)
    @GetMapping("/getMonthCostByRdType")
    @PermissionRequired(perms = "rdagg:aggregation:view")
    @ApiOperation(value = "费用月度数据", notes = "费用月度数据")
    public List< AggSummaryModel > getMonthCostByRdType(Integer year, Integer rdType) throws OwnerException {
        return rdAggService.getMonthCostByRdType(year, rdType, getUserInfo().getCompanyId());
    }
}
