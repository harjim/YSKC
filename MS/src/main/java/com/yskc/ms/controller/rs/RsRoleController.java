package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.models.role.*;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.role.QueryRsRoleModel;
import com.yskc.ms.models.role.UserRoleModel;
import com.yskc.ms.service.rs.RsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.rs
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-30 16:51
 * @Description: rs角色controller层
 */
@Api(value = "rs角色类接口", tags = "rs角色类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsRole")
public class RsRoleController extends BaseController {

    @Autowired
    private RsRoleService rsRoleService;

    @ApiOperation(value = "获取rs角色列表", notes = "获取rs角色列表")
    @PermissionRequired(perms = "customer:companyrole:search,customer:all:setRole")
    @GetMapping("/getList")
    public PageModel<List<RsRoleModel>> getList(QueryRsRoleModel query) {
        return rsRoleService.getList(query);
    }


    @ApiOperation(value = "已分配客户列表", notes = "已分配客户列表")
    @GetMapping("/queryCustomer")
    @PermissionRequired(perms = "customer:companyrole:assignedCustomers")
    public PageModel<List<RsRoleModel>> queryCustomer(Integer roleId, String companyName, int pageNo, int pageSize) {
        return rsRoleService.queryCustomer(roleId, companyName, pageNo, pageSize);
    }

    @ApiOperation(value = "添加用户端角色", notes = "添加用户端角色")
    @PostMapping("/add")
    @PermissionRequired(perms = "customer:companyrole:add")
    @SystemLog(description = "添加用户端角色", mode = SystemLog.SAVE_DB)
    public boolean add(@RequestBody @Validated RsRoleAddModel rsRoleAddModel) throws OwnerException {
        return rsRoleService.add(rsRoleAddModel, getUserInfo());
    }


    @ApiOperation(value = "更新用户端角色", notes = "更新用户端角色")
    @PostMapping("/update")
    @PermissionRequired(perms = "customer:companyrole:edit")
    @SystemLog(description = "更新用户端角色", mode = SystemLog.SAVE_DB)
    public boolean update(@RequestBody @Validated RsRoleAddModel rsRoleAddModel) throws OwnerException {
        return rsRoleService.update(rsRoleAddModel, getUserInfo());
    }

    @ApiOperation(value = "搜索公司", notes = "搜索公司")
    @GetMapping("/getCompany")
    public List<MiniModel> getCompany(String companyName) {
        return rsRoleService.getCompany(companyName);
    }

    @ApiOperation(value = "删除用户端角色", notes = "删除用户端角色")
    @PostMapping("/del")
    @PermissionRequired(perms = "customer:companyrole:del")
    @SystemLog(description = "删除用户端角色", mode = SystemLog.SAVE_DB)
    public boolean del(@RequestBody @Validated RsRoleAddModel rsRoleAddModel) throws OwnerException {
        return rsRoleService.del(rsRoleAddModel.getId());
    }

    @ApiOperation(value = "角色功能列表", notes = "角色功能列表")
    @GetMapping("/getMenuAction")
    public List<RsActionMenuModel> getMenuAction() throws OwnerException {
        return rsRoleService.getMenuByUserId(getUserInfo().getId());
    }

    @ApiOperation(value = "配置角色功能", notes = "配置角色功能")
    @PostMapping("/setRole")
    @PermissionRequired(perms = "customer:companyrole:collocation")
    public boolean setRole(@RequestBody @Validated SetRsRoleModel rsRoleModel) throws OwnerException {
        return rsRoleService.setRole(rsRoleModel, getUserInfo());
    }

    @ApiOperation(value = "分配用户端角色", notes = "分配用户端角色")
    @PostMapping("/setCompanyRole")
    @PermissionRequired(perms = "customer:all:setRole")
    @SystemLog(description = "分配用户端角色", mode = SystemLog.SAVE_DB)
    public boolean setCompanyRole(@RequestBody @Validated CompanyRoleModel companyRoleModel) throws OwnerException {
        return rsRoleService.setCompanyRole(companyRoleModel, getUserInfo());
    }

    @ApiOperation(value = "获取角色功能", notes = "获取角色功能")
    @GetMapping("/getRole")
    public List<Integer> getRole(Integer roleId) {
        return rsRoleService.getRoleMenuIds(roleId);
    }


    @ApiOperation(value = "根据用户获取角色", notes = "根据用户获取角色")
    @GetMapping("/getRoleUserId")
    public List<Integer> getRoleUserId(Integer userId,Integer companyId) throws OwnerException {
        return rsRoleService.getRoleUserId(userId,companyId);
    }

    @PostMapping("/setUserRole")
    @SystemLog(description = "设置用户端用户角色", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:platformAccount")
    @ApiOperation(value = "设置用户端用户角色", notes = "设置用户端用户角色")
    public Boolean setUserRole(@RequestBody @Validated UserRoleModel userRoleModel) throws OwnerException {
        return rsRoleService.setUserRole(userRoleModel, getUserInfo());
    }

    /**
     * 获取公司角色
     *
     * @param roleName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getRoles")
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    public PageModel<List<RsRoleModel>> getRoles(Integer companyId, String roleName, int pageNo, int pageSize) throws OwnerException {
        return rsRoleService.getRoles(companyId, roleName, pageNo, pageSize);
    }


}
