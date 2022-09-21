package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;
import com.yskc.ms.service.ms.MonthlyReportService;
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
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 14:28
 * @Description: 工作报表接口
 */
@RestController
@RequestMapping("/api/monthlyReport")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "工作月报表接口", value = "工作月报表接口")
public class MonthlyReportController extends BaseController {

    @Autowired
    private MonthlyReportService monthlyReportService;

    @GetMapping("/getMonthList")
    @ApiOperation(notes = "获取工作月报表", value = "获取工作月报表")
    @PermissionRequired(perms = "dailyReport:monthReport:search")
    public PageModel<List<Map<String, Object>>> getMonthList(QueryDailyReportModel query) throws OwnerException {
        return monthlyReportService.getMonthList(query, getDataPerm());
    }
}
