package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.patentSupplier.QuerySupplierModel;
import com.yskc.ms.models.patentSupplier.SupplierInfoModel;
import com.yskc.ms.service.ms.PatentSupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/controller/ms
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 专利供应商controller
 */
@Api( tags = "专利供应商接口", value = "专利供应商接口" )
@CrossOrigin( origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {} )
@RestController
@RequestMapping( "/api/patentSupplier" )
public class PatentSupplierController extends BaseController {

    @Autowired
    private PatentSupplierService patentSupplierService;

    @GetMapping( "/getList" )
    @PermissionRequired(perms = "patent:patentSupplier:search")
    @ApiOperation( value = "获取专利供应商列表", notes = "获取专利供应商列表" )
    public PageModel< List< SupplierInfoModel > >  getSupplierList(QuerySupplierModel params) throws OwnerException {
        return patentSupplierService.getSupplierList(params, getUserInfo(), getDataPerm());
    }

    @SystemLog(description = "专利供应商添加")
    @PostMapping("/addSupplier")
    @PermissionRequired(perms = "patent:patentSupplier:add")
    @ApiOperation(value = "添加专利供应商", notes = "添加专利供应商", response = boolean.class)
    public Boolean addSupplier(@RequestBody @Validated SupplierInfoModel infoModel) throws OwnerException {
        return patentSupplierService.saveSupplierInfo(infoModel, getUserInfo(), true);
    }

    @SystemLog(description = "专利供应商编辑")
    @PostMapping("/updateSupplier")
    @PermissionRequired(perms = "patent:patentSupplier:edit")
    @ApiOperation(value = "编辑专利供应商", notes = "编辑专利供应商", response = boolean.class)
    public Boolean editSupplier(@RequestBody @Validated SupplierInfoModel infoModel) throws OwnerException {
        return patentSupplierService.saveSupplierInfo(infoModel, getUserInfo(), false);
    }


    @SystemLog(description = "专利供应商删除")
    @PostMapping("/delSupplier")
    @PermissionRequired(perms = "patent:patentSupplier:del")
    @ApiOperation(value = "删除专利供应商", notes = "删除专利供应商", response = boolean.class)
    public Boolean delSupplier(@RequestBody SupplierInfoModel infoModel) throws OwnerException {
        return patentSupplierService.delSupplierInfo(infoModel);
    }

}
