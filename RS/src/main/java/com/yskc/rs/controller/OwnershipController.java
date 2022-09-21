package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.OwnershipEntity;
import com.yskc.rs.service.OwnershipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 股权架构类接口
 *
 * @author huronghua
 */
@Api(tags = "股权架构类接口", value = "股权架构类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/ownership")
public class OwnershipController extends BaseController {
    @Autowired
    OwnershipService ownershipService;

    /**
     * 查询股权架构
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryOwnershipList")
    @PermissionRequired(perms = "company:info:equity:view")
    @ApiOperation(value = "查询股权架构", notes = "查询股权架构", response = String.class)
    public List<OwnershipEntity> queryOwnershipList() throws OwnerException {
        return ownershipService.queryOwnershipList(getUserInfo().getCompanyId());
    }

    /**
     * 保存股东
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存股东")
    @PostMapping("/add")
    @PermissionRequired(perms = "company:info:equity:add")
    @ApiOperation(value = "保存股东", notes = "保存股东", response = boolean.class)
    public boolean addOwnership(@RequestBody @Validated OwnershipEntity entity) throws OwnerException {
        return ownershipService.addOwnership(getUserInfo(), entity);
    }

    /**
     * 编辑股东
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "编辑股东", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "company:info:equity:edit")
    @ApiOperation(value = "编辑股东", notes = "编辑股东", response = boolean.class)
    public boolean editOwnership(@RequestBody @Validated OwnershipEntity entity) throws OwnerException {
        return ownershipService.editOwnership(getUserInfo(), entity);
    }

    /**
     * 删除股东
     *
     * @param entity
     * @return
     */
    @SystemLog(description = "删除股东", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "company:info:equity:del")
    @ApiOperation(value = "删除股东", notes = "删除股东", response = boolean.class)
    public boolean delOwnership(@RequestBody @Validated OwnershipEntity entity) {
        return ownershipService.delOwnership(entity);
    }

    /**
     * 检查所剩股权比例
     *
     * @param proportion
     * @return
     */
    @GetMapping("/checkProportion")
    @ApiOperation(value = "检查所剩股权比例", notes = "检查所剩股权比例")
    public BigDecimal checkProportion(BigDecimal proportion, BigDecimal oldProportion) throws OwnerException {
        return ownershipService.checkProportion(getUserInfo(), proportion, oldProportion);
    }
}
