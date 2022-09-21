package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.YearCost.YearCostExModel;
import com.yskc.rs.models.YearCost.YearCostModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.YearCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * @program: rs
 * @description: 月度成本管理接口
 * @author: cyj
 * @create: 2022-01-14 11:30
 **/
@Api(tags = "月度成本管理接口", value = "月度成本管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/yearCost")
public class YearCostController extends BaseController{
    @Autowired
    private YearCostService yearCostService;

    @Autowired
    private RsConfig rsConfig;

    @ApiOperation(notes = "获取月度成本列表", value = "获取月度成本列表")
    @GetMapping("/getList")
    @PermissionRequired(perms = "company:yearcost:search")
    public List<YearCostExModel> getList() throws OwnerException {
        return yearCostService.getList(getUserInfo().getCompanyId());
    }
    @ApiOperation(notes = "按年获取月度成本", value = "按年获取月度成本")
    @GetMapping("/getYearCost")
    @PermissionRequired(perms = "company:yearcost:search")
    public YearCostExModel getYearCost(Integer year) throws OwnerException {
        return yearCostService.getYearCost(year,getUserInfo().getCompanyId());
    }
    @ApiOperation(notes = "按月获取月度成本", value = "按月获取月度成本")
    @GetMapping("/getMonthCost")
    @PermissionRequired(perms = "company:yearcost:search")
    public YearCostExModel getMonthCost(Integer year, Integer month) throws OwnerException {
        return yearCostService.getMonthCost(year,month,getUserInfo().getCompanyId());
    }
    @ApiOperation(notes = "添加/编辑月度成本", value = "添加/编辑月度成本")
    @SystemLog(description = "添加/编辑月度成本", mode = SystemLog.SAVE_DB)
    @PostMapping("/save")
    @PermissionRequired(perms = "company:yearcost:add,company:yearcost:edit")
    public Boolean saveYearCost(@RequestBody YearCostExModel model) throws OwnerException {
        model.setCompanyId(getUserInfo().getCompanyId());
        return yearCostService.saveYearCost(model,getUserInfo());
    }
    @ApiOperation(notes = "批量删除月度成本", value = "批量删除月度成本")
    @SystemLog(description = "批量删除月度成本", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "company:yearcost:del")
    public Boolean delYearCost(@RequestBody List<Object> years) throws OwnerException {
        return yearCostService.delYearCost(years,getUserInfo());
    }
}
