package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.models.company.QueryCompanyModel;
import com.yskc.ms.models.params.CompanyParams;
import com.yskc.ms.service.rs.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司类接口
 *
 * @author huronghua
 */
@Api(tags = "公司类接口", value = "公司类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/queryCompanyList")
    @ApiOperation(value = "查询客户信息", notes = "查询客户信息", response = String.class)
    @PermissionRequired(perms = "customer:register:search")
    public PageModel<List<CompanyModel>> queryCompanyList(QueryCompanyModel query) throws OwnerException {
        return companyService.queryCompanyList(query);
    }

    @GetMapping("/companyList")
    @PermissionRequired(perms = "customer:all:search")
    @ApiOperation(value = "客户列表", notes = "客户列表", response = String.class)
    public PageModel<List<CompanyModel>> getCompanyList(CompanyParams params) throws OwnerException {
        return companyService.getCompanyList(params);
    }

    @PostMapping("/save")
    @SystemLog(description = "保存公司信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:register:examine")
    @ApiOperation(value = "保存公司信息", notes = "保存公司信息", response = boolean.class)
    public Integer saveCompany(@RequestBody @Validated CompanyModel model) throws OwnerException {
        return companyService.saveCompany(model, getUserInfo());
    }

    @PostMapping("/addCustomer")
    @SystemLog(description = "添加公司信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:add")
    @ApiOperation(value = "添加公司信息", notes = "添加公司信息", response = boolean.class)
    public boolean addCustomer(@RequestBody @Validated CompanyModel model) throws OwnerException {
        return companyService.addCustomer(model, getUserInfo().getId());
    }

    @PostMapping("/editCustomer")
    @SystemLog(description = "更新公司信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "更新公司信息", notes = "更新公司信息", response = boolean.class)
    public boolean editCustomer(@RequestBody @Validated CompanyModel model) throws OwnerException {
        return companyService.editCustomer(model, getUserInfo().getId());
    }

    @PostMapping("/jump")
    @SystemLog(description = "登录到用户端", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:my:jumpPage")
    @ApiOperation(value = "登录到用户端", notes = "登录到用户端")
    public String jump(@RequestBody @Validated MyCustomerModel model) throws OwnerException {
        return companyService.jump(model, getUserInfo(),this.getDataPerm());
    }

    @PostMapping("/updateCompany")
    @SystemLog(description = "审核公司失败(客户已存在)", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:register:examine")
    @ApiOperation(value = "审核公司失败(客户已存在)", notes = "审核公司失败(客户已存在)", response = boolean.class)
    public boolean updateCompany(@RequestBody @Validated CompanyModel model) throws OwnerException {
        return companyService.updateCompany(model, getUserInfo());
    }

}