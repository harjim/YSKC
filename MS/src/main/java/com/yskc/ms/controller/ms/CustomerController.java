package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.Customer;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.company.NameHistoryModel;
import com.yskc.ms.models.customer.*;
import com.yskc.ms.service.ms.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "客户类接口", value = "客户类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customerForSelect")
    @ApiOperation(value = "查询客户下拉框(带地址)", notes = "查询客户下拉框(带地址)", response = List.class)
    public List<MiniCustomerModel> customerForSelect(String customerName, String addressStr, Integer deptId) {
        return customerService.customerForSelect(customerName, addressStr, deptId);
    }

    @GetMapping("/getCustomerSelect")
    @ApiOperation(value = "查询客户下拉框", notes = "查询客户下拉框")
    public List<MiniModel> getCustomerSelect(String customerName) {
        return customerService.getCustomerSelect(customerName);
    }

    @GetMapping("/getCustomerOwner")
    @ApiOperation(value = "查询客户下拉框(带业务员)", notes = "查询客户下拉框(带业务员)")
    public List<CustomerOwnerModel> getCustomerOwner(String customerName) {
        return customerService.getCustomerList(customerName);
    }

    @GetMapping("/getCompanySelect")
    @ApiOperation(value = "查询公司下拉框", notes = "查询公司下拉框")
    public List<MiniModel> getCompanySelect(String customerName) {
        return customerService.getCompanySelect(customerName);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "customer:all:search,customer:unsigned:search")
    @ApiOperation(value = "获取客户列表", notes = "获取客户列表")
    public PageModel<List<CustomerModel>> getList(QueryCustomerModel queryCustomerModel) throws OwnerException {
        return customerService.getList(getUserInfo(), queryCustomerModel, getDataPerm(),queryCustomerModel.getUnsigned());
    }

    @SystemLog(description = "开通账号", mode = SystemLog.SAVE_DB)
    @PostMapping("/openingAccount")
    @PermissionRequired(perms = "customer:all:addAccount")
    @ApiOperation(value = "开通账号", notes = "开通账号", response = boolean.class)
    public boolean openingAccount(@RequestBody @Validated CompanyModel model) throws OwnerException {
        return customerService.openingAccount(model, getUserInfo());
    }

    @SystemLog(description = "跟进", mode = SystemLog.SAVE_DB)
    @PostMapping("/followUp")
    @PermissionRequired(perms = "customer:all:follow")
    @ApiOperation(value = "跟进", notes = "跟进", response = boolean.class)
    public boolean followUp(@RequestBody @Validated QueryFollowModel query) throws OwnerException {
        return customerService.followUp(query,getUserInfo());
    }

    @SystemLog(description = "批量跟进", mode = SystemLog.SAVE_DB)
    @PostMapping("/followUpList")
    @PermissionRequired(perms = "customer:unsigned:follow")
    @ApiOperation(value = "批量跟进", notes = "批量跟进", response = boolean.class)
    public boolean followUpList(@RequestBody @Validated QueryFollowListModel query) throws OwnerException {
        return customerService.followUpList(query,getUserInfo());
    }

    @GetMapping("/getTraceList")
    @PermissionRequired(perms = "customer:all:followLog,customer:unsigned:followLog")
    @ApiOperation(value = "获取跟进记录", notes = "获取跟进记录")
    public PageModel<List<CustomerTraceModel>> getTraceList(QueryTraceModel query) throws OwnerException {

        return customerService.getTraceList(query,getUserInfo());
    }

    @GetMapping("/getNameHistory")
    @PermissionRequired(perms = "customer:all:changeName")
    @ApiOperation(value = "获取变更名称历史记录", notes = "获取变更名称历史记录", response = boolean.class)
    public List< NameHistoryModel > getNameHistory(@RequestParam("companyId") Integer companyId) throws OwnerException {
        return customerService.getNameHistory(companyId);
    }

    @SystemLog(description = "保存公司变更名称记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveNameHistory")
    @PermissionRequired(perms = "customer:all:changeName")
    @ApiOperation(value = "保存公司变更名称记录", notes = "保存公司变更名称记录", response = boolean.class)
    public Integer saveNameHistory(@RequestBody @Validated NameHistoryModel nameHistoryModel) throws OwnerException {
        return customerService.saveNameHistory(nameHistoryModel,getUserInfo());
    }

    @SystemLog(description = "删除公司变更名称记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/delNameHistory")
    @PermissionRequired(perms = "customer:all:changeName")
    @ApiOperation(value = "删除公司变更名称记录", notes = "删除公司变更名称记录", response = boolean.class)
    public Integer delNameHistory(@RequestBody @Validated NameHistoryModel nameHistoryModel) throws OwnerException {
        return customerService.delNameHistory(nameHistoryModel,getUserInfo());
//        return 1;
    }

    @SystemLog(description = "变更名称", mode = SystemLog.SAVE_DB)
    @PostMapping("/changeName")
    @PermissionRequired(perms = "customer:all:changeName")
    @ApiOperation(value = "变更名称", notes = "变更名称", response = boolean.class)
    public boolean changeName(@RequestBody @Validated CustomerModel model) throws OwnerException {
        return customerService.changeName(model.getId(), model.getCompanyName(), getUserInfo());
    }

    @SystemLog(description = "批量指定业务员", mode = SystemLog.SAVE_DB)
    @PostMapping("/setOwnerUser")
    @PermissionRequired(perms = "customer:all:setOwner")
    @ApiOperation(value = "批量指定业务员", notes = "批量指定业务员")
    public boolean setOwnerUser(@RequestBody @Validated CustomerOwnerUserModel customerOwnerUserModel) throws OwnerException {
        return customerService.setOwnerUser(getUserInfo(), customerOwnerUserModel);
    }

    @PostMapping("/addCustomer")
    @PermissionRequired(perms = "customer:all:add")
    @SystemLog(description = "添加客户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加客户", notes = "添加客户", response = boolean.class)
    public boolean addCustomer(@RequestBody @Validated CustomerModel model) throws OwnerException {
        Integer userId = getUserInfo().getId();
        return customerService.addCustomer(model, userId, getUserInfo());
    }

    @PostMapping("/editCustomer")
    @PermissionRequired(perms = "customer:all:edit")
    @SystemLog(description = "更新客户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新客户", notes = "更新客户", response = boolean.class)
    public boolean editCustomer(@RequestBody @Validated CustomerModel model) throws OwnerException {
        return customerService.editCustomer(model, getUserInfo());
    }

    @GetMapping("/checkCompanyName")
    @ApiOperation(value = "检查客户名是否已存在", notes = "检查客户名是否已存在")
    public boolean checkCompanyName(String companyName) throws OwnerException {
        return customerService.checkCompanyName(companyName);
    }

    @GetMapping("/getMyCustomerList")
    @PermissionRequired(perms = "customer:my:search")
    @ApiOperation(value = "获取我的客户列表", notes = "获取我的客户列表")
    public PageModel<List<MyCustomerModel>> getMyCustomerList(QueryCustomerModel query) throws OwnerException {
        return customerService.getMyCustomerList(query, this.getDataPerm());
    }

    @GetMapping("/getGroupSelect")
    @ApiOperation(value = "获取集团下拉【包括总公司】", notes = "获取集团下拉【包括总公司】")
    public List<MiniModel> getGroupSelect(Integer groupId) {
        return customerService.getGroupSelect(groupId);
    }

    @PostMapping("/setGroup")
    @PermissionRequired(perms = "customer:all:setGroup")
    @SystemLog(description = "集团设置", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "集团设置", notes = "集团设置")
    public Integer setGroup(@RequestBody @Validated Customer customer) throws OwnerException {
        return customerService.setGroup(getUserInfo().getId(), customer.getId());
    }

    @GetMapping("/findCustomerList")
    @ApiOperation(value = "获取客户列表", notes = "获取客户列表")
    public PageModel<List<FindCustomerModel>> findCustomerList(FindCustomerModel query) throws OwnerException {
        return customerService.findCustomerList(query);
    }

    @GetMapping("/findSonCustomerList")
    @PermissionRequired(perms = "customer:all:sonCustomer")
    @ApiOperation(value = "获取集团子公司列表", notes = "获取集团子公司列表")
    public PageModel<List<FindCustomerModel>> findSonCustomerList(FindCustomerModel model) {
        return customerService.findSonCustomerList(model);
    }

    @PostMapping("/insertSonCustomer")
    @PermissionRequired(perms = "customer:all:addSon")
    @SystemLog(description = "新增集团子公司", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "新增集团子公司", notes = "新增集团子公司")
    public Boolean insertSonCustomer(@RequestBody @Validated CustomerParamsModel params) throws OwnerException {
        return customerService.insertSonCustomer(params.getCompanyId(),getUserInfo().getId(), params.getGroupId(), params.getIds());
    }

    @PostMapping("/delSonCustomer")
    @PermissionRequired(perms = "customer:all:delSon")
    @SystemLog(description = "删除集团子公司", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除集团子公司", notes = "删除集团子公司")
    public Boolean delSonCustomer(@RequestBody @Validated CustomerParamsModel params) throws OwnerException {
        return customerService.delSonCustomer(getUserInfo().getId(), params.getCompanyId(),params.getIds());
    }


}
