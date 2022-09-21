package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.service.rs.BudgetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    @PermissionRequired(perms = "customer:finalization:rdDetail")
    @ApiOperation(value = "查询项目资金预算", notes = "查询项目资金预算", response = String.class)
    public Map<String, Object> queryBudgetList(Integer projectId) throws OwnerException {
        return budgetService.queryBudgetList(projectId);
    }

    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAllBudget")
    @PermissionRequired(perms = "customer:finalization:rdDetail")
    @ApiOperation(value = "查询项目各月资金预算", notes = "查询项目各月资金预算", response = String.class)
    public Map<String, BigDecimal> queryAllBudget(Integer projectId) {
        return budgetService.queryAllBudget(projectId);
    }

    /**
     * 查询预算字典
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getSysDictionaryModelist")
    @ApiOperation(value = "查询预算字典", notes = "查询预算字典", response = String.class)
    public List<SysDictionaryModel> getSysDictionaryModelist() throws OwnerException {
        return budgetService.getSysDictionaryModel();
    }

}
