package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.CustomerTotalModel;
import com.yskc.ms.service.rs.SummaryService;
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
 * @BelongsPackage: com.yskc.ms.controller.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-18 13:47
 * @Description: 归集汇总接口
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(value = "归集汇总接口", tags = "归集汇总接口")
@RequestMapping("/api/summary")
public class SummaryController extends BaseController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/getCustomerTotal")
    @PermissionRequired(perms = "index:total")
    @ApiOperation(value = "获取客户数/项目数/归集额", notes = "获取客户数/项目数/归集额")
    public CustomerTotalModel getCustomerTotal() {
        return summaryService.getCustomerTotal();
    }

    @GetMapping("/getDistrictSummary")
    @PermissionRequired(perms = "index:district")
    @ApiOperation(value = "按年获取部门区域归集费", notes = "按年获取部门区域归集费")
    public List<Map<String, Object>> getDistrictSummary(Integer year) {
        return summaryService.getDistrictSummary(year);
    }

    @GetMapping("/getBranchTotal")
    @PermissionRequired(perms = "index:branch")
    @ApiOperation(value = "获取分公司年客户数/项目数/归集额", notes = "获取分公司年客户数/项目数/归集额")
    public List<List<Map<String, Object>>> getBranchTotal(Integer year) {
        return summaryService.getBranchTotal(year);
    }

    @GetMapping("/getSummaryCost")
    @ApiOperation(value = "获取研发费用", notes = "获取研发费用")
    public List<Map<String, Object>> getSummaryCost(Integer year) {
        return summaryService.getSummaryCost(year);
    }


}
