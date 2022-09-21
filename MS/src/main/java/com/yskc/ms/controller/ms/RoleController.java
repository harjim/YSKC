package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.role.*;
import com.yskc.ms.service.ms.AppRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单类接口
 *
 * @author huronghua
 */
@Api(tags = "角色类接口", value = "角色类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {
    @Autowired
    private AppRoleService appRoleService;

    /**
     * 测试
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAppRoleList")
    @PermissionRequired(perms = "sys:role:search,sys:user:setUserRole,sys:user:assign")
    @ApiOperation(value = "获取权限", notes = "获取权限")
    public PageModel<List<AppRoleModel>> getAppRoleList(QueryRoleModel query) throws OwnerException {
        return appRoleService.getAppRoleList(query, getUserInfo(),getDataPerm());
    }

    /**
     * 添加角色
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/add")
    @PermissionRequired(perms = "sys:role:add")
    @SystemLog(description = "添加角色", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加角色", notes = "添加角色", response = boolean.class)
    public boolean addRole(@RequestBody @Validated RoleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appRoleService.addRole(userInfo.getId(), model);
    }

    /**
     * 删除角色
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/del")
    @SystemLog(description = "删除角色", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:role:del")
    @ApiOperation(value = "删除角色", notes = "删除角色", response = boolean.class)
    public boolean delRole(@RequestBody @Validated RoleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appRoleService.delRole(userInfo.getId(), model);
    }

    /**
     * 更新角色
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/update")
    @SystemLog(description = "更新角色", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:role:edit")
    @ApiOperation(value = "更新角色", notes = "更新角色", response = boolean.class)
    public boolean updateRole(@RequestBody @Validated RoleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appRoleService.updateRole(userInfo.getId(), model);
    }

    /**
     * 获取菜单权限
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getMenuAction")
    @ApiOperation(value = "获取菜单权限", notes = "获取菜单权限")
    public List<MenuInfo> getMenuAction() throws OwnerException {
        return appRoleService.getRoleMenuMap();
    }

    /**
     * 获取角色菜单权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/getRoleMenuIds")
    @ApiOperation(value = "获取角色菜单权限", notes = "获取角色菜单权限")
    public List<RoleDataModel> getRoleMenuIds(Integer roleId) {
        return appRoleService.getRoleMenuIds(roleId);
    }

    /**
     * 配置角色权限
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/set")
    @SystemLog(description = "配置管理端角色权限", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "配置管理端角色权限", notes = "配置管理端角色权限", response = boolean.class)
    @PermissionRequired(perms = "sys:role:set")
    public boolean setRole(@RequestBody @Validated SetRoleModel model) throws OwnerException {
        return appRoleService.setRole(getUserInfo().getId(), model);
    }

    @PostMapping("/setUserRole")
    @SystemLog(description = "配置管理端用户角色", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "配置管理端用户角色", notes = "配置管理端用户角色", response = boolean.class)
    @PermissionRequired(perms = "sys:user:assign")
    public boolean setUserRole(@RequestBody @Validated UserRoleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appRoleService.setUserRole(userInfo.getId(), model);
    }

    @PostMapping("/setUserRoles")
    @SystemLog(description = "批量配置管理端用户角色", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量配置管理端用户角色", notes = "批量配置管理端用户角色", response = boolean.class)
    @PermissionRequired(perms = "sys:user:assign")
    public boolean setUserRoles(@RequestBody @Validated UserRoleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appRoleService.setUserRoles(userInfo.getId(), model);
    }

    /**
     * 获取用户角色关联信息
     *
     * @param userId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getUserRoleIds")
    @ApiOperation(value = "获取用户角色关联信息", notes = "获取用户角色关联信息", response = UserRoleModel.class)
    @PermissionRequired(perms = "sys:user:assign,sys:user:setUserRole,sys:user:assign")
    public UserRoleModel getUserRoleIds(Integer userId) throws OwnerException {
        return appRoleService.getUserRoleIds(userId);
    }


    @PostMapping("/delRoleBatch")
    @ApiOperation(value = "批量删除", notes = "批量删除", response = boolean.class)
    public boolean delBatch(@RequestBody @Validated List<RoleModel> modelList) throws OwnerException {
        return appRoleService.delBatch(modelList);
    }


    @GetMapping("/getUserByRoleId")
    @ApiOperation(value = "根据角色获取用户", notes = "根据角色获取用户")
    public PageModel<List<DeptUserInfo>> getUserByRoleId(QueryRoleManageModel query) throws OwnerException {
        return appRoleService.getUserByRoleId(query);
    }

    @GetMapping("/selectUserData")
    @ApiOperation(value = "获取未曾设置角色的列表", notes = "获取未曾设置角色的列表")
    public PageModel<List<DeptUserInfo>> selectUserData(int pageNo, int pageSize, Integer roleId, String realName, String fullPath) throws OwnerException {
        return appRoleService.selectUserData(pageNo, pageSize, roleId, realName, fullPath);

    }

    @PostMapping("/deleteBatchUser")
    @ApiOperation(value = "批量删除角色下的用户", notes = "批量删除角色下的用户", response = boolean.class)
    public boolean deleteBatchUser(@RequestBody @Validated List<DeptUserInfo> list) throws OwnerException {
        return appRoleService.deleteBatchUser(list);
    }

    @PostMapping("/updateServiceDept")
    @ApiOperation(value = "设置服务部门", notes = "设置服务部门", response = boolean.class)
    public boolean updateServiceDept(@RequestBody UserDept userDept){
        return appRoleService.updateServiceDept(userDept);
    }

}
