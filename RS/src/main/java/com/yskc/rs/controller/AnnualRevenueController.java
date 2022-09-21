package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.revenuemanage.AnnualRevenueModel;
import com.yskc.rs.models.revenuemanage.RevenueModel;
import com.yskc.rs.service.RevenueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/1/14 8:12
 * @Description:年度营收管理
 * @author: hsx
 */
@Api(tags = "年度营收管理接口", value = "年度营收管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/revenue")
public class AnnualRevenueController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "company:revenue:search")
    @ApiOperation(value = "获取年度营收", notes = "获取年度营收")
    public List<AnnualRevenueModel> getList(Integer year) throws OwnerException {
        return revenueService.getList(year,getUserInfo().getCompanyId());
    }

    @PostMapping("/add")
    @PermissionRequired(perms = "company:revenue:add")
    @SystemLog(description = "新增年度营收", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "新增年度营收", notes = "新增年度营收")
    public Boolean add(@RequestBody AnnualRevenueModel model) throws Exception {
        UserInfo info = getUserInfo();
        return revenueService.add(model,info);
    }

    @PostMapping("/edit")
    @PermissionRequired(perms = "company:revenue:edit")
    @SystemLog(description = "编辑年度营收", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑年度营收", notes = "编辑年度营收")
    public Boolean edit(@RequestBody AnnualRevenueModel model) throws Exception {
        UserInfo info = getUserInfo();
        return revenueService.edit(model,info);
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "company:revenue:del")
    @SystemLog(description = "删除年度营收", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除年度营收", notes = "删除年度营收")
    public Boolean del(@RequestBody AnnualRevenueModel model) throws OwnerException {
        return revenueService.del(model.getYear(), getUserInfo().getCompanyId());
    }

    @GetMapping("/getData")
    @ApiOperation(value = "获取年度营收数据", notes = "获取年度营收数据")
    public AnnualRevenueModel getData(Integer year) throws OwnerException {
        return revenueService.getData(year,getUserInfo().getCompanyId());
    }
}
