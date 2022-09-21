package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.product.*;
import com.yskc.rs.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: rs
 * @description: 企业产品
 * @author: wjy
 * @create: 2022-06-09 08:32
 **/
@Api(tags = "企业产品接口", value = "企业产品接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/product")
public class ProductController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "company:product:search")
    @ApiOperation(value = "获取企业产品列表", notes = "获取企业产品列表")
    public PageResult getList(QueryProductModel query) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        return productService.getList(query,companyId);
    }

    @GetMapping("/checkPcode")
    @PermissionRequired(perms = "company:product:search")
    @ApiOperation(value = "检查产品编码", notes = "检查产品编码")
    public Boolean checkPcode(QueryPcodeModel query) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        return productService.checkPcode(query,companyId);
    }

    @ApiOperation(notes = "删除企业产品", value = "删除企业产品")
    @SystemLog(description = "删除企业产品", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "company:product:delete")
    public boolean del(@RequestBody @Validated ProductModel model) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        return productService.deleteProduct(model.getId(),companyId);
    }

    @ApiOperation(notes = "添加企业产品", value = "添加企业产品")
    @SystemLog(description = "添加企业产品", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "company:product:add")
    public boolean add(@RequestBody @Validated ProductModel model) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        return productService.addProduct(model,companyId,getUserInfo());
    }

    @ApiOperation(notes = "编辑企业产品", value = "编辑企业产品")
    @SystemLog(description = "编辑企业产品", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "company:product:edit,company:product:heightEdit")
    public boolean editProduct(@RequestBody @Validated ProductModel model) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        model.setCompanyId(companyId);
        return productService.updateProduct(model,getUserInfo());
    }

    @GetMapping("/getYearList")
    @PermissionRequired(perms = "company:product:search")
    @ApiOperation(value = "获取企业产品产值列表", notes = "获取企业产品产值列表")
    public List<ProductYearModel> getYearList(Integer productId) throws OwnerException {
        Integer companyId = getUserInfo().getCompanyId();
        return productService.getYearList(productId,companyId);
    }

    @ApiOperation(notes = "编辑企业产品产值", value = "编辑企业产品产值")
    @SystemLog(description = "编辑企业产品产值", mode = SystemLog.SAVE_DB)
    @PostMapping("/editProductYear")
    @PermissionRequired(perms = "company:product:outputManage")
    public boolean editProductYear(@RequestBody @Validated QueryProductYearModel models) throws OwnerException {
        return productService.updateProductYear(models.getModels(),getUserInfo(), models.getProductId());
    }
}
