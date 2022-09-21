package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.BudgetAddandUpdateModel;
import com.yskc.rs.models.project.BudgetInfoModel;
import com.yskc.rs.models.project.ReportBudgetModel;
import com.yskc.rs.service.BudgetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-10-29 11:06
 * @Description: 项目资金预算Controller
 */
@Api(tags = "项目资金预算接口", value = "项目资金预算接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/budget")
public class BudgetController extends BaseController {
    @Autowired
    private BudgetService budgetService;

    /**
     * 查询项目资金预算
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryBudgetList")
    @PermissionRequired(perms = "project:report:budget:search,project:doc:view")
    @ApiOperation(value = "查询项目资金预算", notes = "查询项目资金预算", response = String.class)
    public Map<String, Map<String,Object>> queryBudgetList(Integer projectId) throws OwnerException {
        return budgetService.queryBudget(projectId, true);
    }
    /**
     * 同步归集费用
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getBudgetList")
    @PermissionRequired(perms = "project:report:budget:search,project:doc:view")
    @ApiOperation(value = "同步归集费用", notes = "同步归集费用", response = String.class)
    public Map<String, Map<String,Object>> getBudgetList(Integer projectId) throws OwnerException {
        return budgetService.queryBudget(projectId, false);
    }


    /**
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryProjectBudget")
    @ApiOperation(value = "立项报告项目资金预算", notes = "立项报告项目资金预算", response = String.class)
    public List<ReportBudgetModel> queryProjectBudget(Integer projectId) throws OwnerException {
        UserInfo info = getUserInfo();
        return budgetService.queryProjectBudget(info.getCompanyId(), projectId);
    }

    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAllBudget")
    @PermissionRequired(perms = "project:report:budget:search")
    @ApiOperation(value = "查询项目各月资金预算", notes = "查询项目各月资金预算", response = String.class)
    public Map<String, Object> queryAllBudget(Integer projectId) {
        return budgetService.queryAllBudget(projectId);
    }

    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryTotalBudget")
    @PermissionRequired(perms = "project:report:budget:search,project:doc:edit")
    @ApiOperation(value = "查询项目总资金预算", notes = "查询项目总资金预算")
    public BigDecimal queryTotalBudget(Integer projectId) {
        return budgetService.queryTotalBudget(projectId);
    }

    /**
     * 修改
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存项目月预算", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "project:report:budget:save")
    @ApiOperation(value = "保存项目月预算", notes = "保存项目月预算", response = boolean.class)
    public boolean update(@RequestBody @Validated BudgetAddandUpdateModel model) throws OwnerException {
        return budgetService.update(getUserInfo(), model);
    }


    @SystemLog(description = "保存项目经费来源预算", mode = SystemLog.SAVE_DB)
    @PostMapping("/save")
    @PermissionRequired(perms = "project:report:budget:save")
    @ApiOperation(value = "保存项目经费来源预算", notes = "保存项目经费来源预算", response = boolean.class)
    public boolean save(@RequestBody @Validated BudgetInfoModel model) throws OwnerException {
        return budgetService.save(getUserInfo(), model);
    }

    /**
     * 查询所有信息
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getSysDictionaryModelist")
    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", response = String.class)
    public List<SysDictionaryModel> getSysDictionaryModelist() throws OwnerException {
        return budgetService.getSysDictionaryModel();
    }
}
