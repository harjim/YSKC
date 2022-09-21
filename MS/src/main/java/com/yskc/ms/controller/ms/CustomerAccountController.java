package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.CustomerAccount;
import com.yskc.ms.entity.ms.models.CustomerAccountModel;
import com.yskc.ms.entity.ms.models.QueryCustomerAccountModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.ms.CustomerAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-18 09:17
 * @Description: 客户账号管理
 */

@Api(tags = "客户账号管理类接口", value = "客户账号管理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/customerAccount")
public class CustomerAccountController extends BaseController {
    @Autowired
    private CustomerAccountService customerAccountService;

    @GetMapping("/queryCustomerAccountList")
    @PermissionRequired(perms = "customer:account:search")
    @ApiOperation(value = "获取所有客户账户信息", notes = "获取所有客户账户信息")
    public PageModel<List<CustomerAccountModel>> queryCustomerAccountList(QueryCustomerAccountModel query) throws OwnerException {
        return customerAccountService.queryCustomerAccount(query, getDataPerm());
    }

    @SystemLog(description = "添加客户账户", mode = SystemLog.SAVE_DB)
    @PostMapping("/insertCustomerAccount")
    @PermissionRequired(perms = "customer:account:add")
    @ApiOperation(value = "添加客户账户", notes = "添加客户账户", response = boolean.class)
    public boolean insertCustomerAccount(@RequestBody @Validated CustomerAccountModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return customerAccountService.insertCustomerAccount(userInfo, model);
    }


    @SystemLog(description = "修改客户账户", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateCustomerAccount")
    @PermissionRequired(perms = "customer:account:edit")
    @ApiOperation(value = "修改客户账户", notes = "修改客户账户", response = boolean.class)
    public boolean updateCustomerAccount(@RequestBody @Validated CustomerAccountModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return customerAccountService.updateCustomerAccount(userInfo, model);
    }


    @SystemLog(description = "刪除客户账户", mode = SystemLog.SAVE_DB)
    @PostMapping("/delCustomerAccount")
    @PermissionRequired(perms = "customer:account:del")
    @ApiOperation(value = "刪除客户账户", notes = "刪除客户账户", response = boolean.class)
    public boolean delCustomerAccount(@RequestBody @Validated CustomerAccountModel model) throws OwnerException {
        return customerAccountService.delCustomerAccount(model.getId());
    }


    @SystemLog(description = "批量刪除客户账户", mode = SystemLog.SAVE_DB)
    @PostMapping("/delCustomerAccountList")
    @PermissionRequired(perms = "customer:account:delBatch")
    @ApiOperation(value = "批量刪除客户账户", notes = "批量刪除客户账户", response = boolean.class)
    public boolean delCustomerAccountList(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return customerAccountService.delCustomerAccountList(deleteModel);
    }

    @GetMapping("/getDataById")
    @PermissionRequired(perms = "customer:account:seePassword,customer:account:edit")
    @ApiOperation(value = "查看密码", notes = "查看密码", response = boolean.class)
    public CustomerAccount getDataById(Integer id) throws OwnerException {
        return customerAccountService.getDataById(id);
    }

    @ApiOperation(value = "搜索系统名称", notes = "搜索系统名称")
    @GetMapping("/getDataByPlatform")
    public List<QueryCustomerAccountModel> getDataByPlatform(String platform) {
        return customerAccountService.getDataByPlatform(platform);
    }

    @ApiOperation(value = "搜索网址", notes = "搜索网址")
    @GetMapping("/getDataBypUrl")
    public List<QueryCustomerAccountModel> getDataBypUrl(String pUrl) {
        return customerAccountService.getDataBypUrl(pUrl);
    }


}
