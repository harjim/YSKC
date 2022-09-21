package com.yskc.ms.controller.mobile;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.customer.*;
import com.yskc.ms.models.dept.DeptTree;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.ms.CustomerService;
import com.yskc.ms.service.ms.DeptService;
import com.yskc.ms.service.ms.UserService;
import com.yskc.ms.service.rs.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description: 移动端客户管理
 * @author: cyj
 * @create: 2022-06-30 09:51
 **/
@RestController
@RequestMapping("/api/mobile/customer")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "移动端客户管理", value = "移动端客户管理")
public class MobileCustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @PostMapping("/addMobileCustomer")
    @PermissionRequired(perms = "mobile:index:addCustomer")
    @SystemLog(description = "添加客户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加客户", notes = "添加客户", response = boolean.class)
    public boolean addCustomer(@RequestBody @Validated CustomerModel model) throws OwnerException {
        Integer userId = getUserInfo().getId();
        return customerService.addCustomer(model, userId, getUserInfo());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "mobile:customer:search")
    @ApiOperation(value = "获取客户列表", notes = "获取客户列表")
    public PageModel<List<CustomerModel>> getList(QueryCustomerModel queryCustomerModel) throws OwnerException {
        return customerService.getMobileList(getUserInfo(), queryCustomerModel, getDataPerm(),queryCustomerModel.getUnsigned());
    }

    @GetMapping("/checkCompanyName")
    @ApiOperation(value = "检查客户名是否已存在", notes = "检查客户名是否已存在")
    public boolean checkCompanyName(String companyName) throws OwnerException {
        return customerService.checkCompanyName(companyName);
    }

    @PostMapping("/editBasicInfo")
    @SystemLog(description = "编辑基本信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "mobile:customer:edit")
    @ApiOperation(value = "编辑基本信息", notes = "编辑基本信息", response = boolean.class)
    public Boolean editBasicInfo(@RequestBody @Validated CustomerModel model) throws OwnerException {
        return customerService.editCustomer(model, getUserInfo());
    }

    @GetMapping("/tree")
    @ApiOperation(value = "部门树", notes = "部门树", response = List.class)
    public List<DeptTree> tree() {
        return DeptTree.getTree(deptService.queryAll());
    }

    @GetMapping("/userForSelect")
    @ApiOperation(value = "查询用户下拉框(带部门)", notes = "查询用户下拉框(带部门)", response = List.class)
    public List<MiniUserModel> userForSelect(String realName, String fullPath, Boolean hasDept, Boolean noLeave) {
        return userService.userForDept(realName, fullPath, hasDept,noLeave);
    }

    @GetMapping("/getTraceList")
//    @PermissionRequired(perms = "mobile:customer:")
    @ApiOperation(value = "获取跟进记录", notes = "获取跟进记录")
    public PageModel<List<CustomerTraceModel>> getTraceList(QueryTraceModel query) throws OwnerException {

        return customerService.getMobileTraceList(query,getUserInfo());
    }

    @SystemLog(description = "跟进", mode = SystemLog.SAVE_DB)
    @PostMapping("/followUp")
    @PermissionRequired(perms = "mobile:customer:follow")
    @ApiOperation(value = "跟进", notes = "跟进", response = boolean.class)
    public boolean followUp(@RequestBody @Validated QueryFollowModel query) throws OwnerException {
        return customerService.followUp(query,getUserInfo());
    }

}
