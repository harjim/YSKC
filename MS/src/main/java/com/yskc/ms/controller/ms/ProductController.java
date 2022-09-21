package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.ProductModel;
import com.yskc.ms.models.product.QueryProductModel;
import com.yskc.ms.service.ms.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "产品类接口", value = "产品类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/queryProduct")
    @PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "查询产品列表", notes = "查询产品列表", response = List.class)
    public PageModel<List<ProductModel>> queryProduct(QueryProductModel query) throws OwnerException{
        return productService.queryProduct(query,getDataPerm());
    }

    @PostMapping("/addProduct")
    @PermissionRequired(perms = "sys:product:add")
    @ApiOperation(value = "添加产品", notes = "添加产品", response = boolean.class)
    public String addProduct(@RequestBody @Validated ProductModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return productService.addProduct(userInfo.getId(), model);
    }

    @PostMapping("/editProduct")
    @PermissionRequired(perms = "sys:product:edit")
    @ApiOperation(value = "编辑产品", notes = "编辑产品", response = boolean.class)
    public String editProduct(@RequestBody @Validated ProductModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return productService.editProduct(userInfo.getId(), model);
    }

    @PostMapping("/deleteProduct")
    @PermissionRequired(perms = "sys:product:del")
    @ApiOperation(value = "删除产品", notes = "删除产品", response = boolean.class)
    public boolean deleteProduct(@RequestBody @Validated ProductModel model) throws OwnerException {
        return productService.deleteProduct(model);
    }

    @GetMapping("/productForSelect")
    @ApiOperation(value = "查询产品下拉框", notes = "查询产品下拉框", response = List.class)
    public List<ProductModel> productForSelect(Integer year,String addressCode,String productName) {
        return productService.productForSelect(year,addressCode,productName);
    }

}
