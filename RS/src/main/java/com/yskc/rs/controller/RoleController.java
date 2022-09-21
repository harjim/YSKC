package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.role.AddUserRoleModel;
import com.yskc.rs.models.role.MenuActionModel;
import com.yskc.rs.models.role.RoleModel;
import com.yskc.rs.models.role.AppRoleModel;
import com.yskc.rs.service.AppRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * @param pageNo
     * @param pageSize
     * @param roleName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAppRoleList")
    @ApiOperation(value = "获取权限", notes = "获取权限")
    public PageModel<List<AppRoleModel>> getAppRoleList(int pageNo, int pageSize, String roleName) throws OwnerException {
        return appRoleService.getAppRoleList(pageNo, pageSize, roleName);
    }

    /**
     * 添加角色
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/add")
    @SystemLog(description = "添加角色",mode = SystemLog.SAVE_DB)
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
    @SystemLog(description = "删除角色",mode = SystemLog.SAVE_DB)
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
    @SystemLog(description = "更新角色",mode = SystemLog.SAVE_DB)
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
    public List<MenuActionModel> getMenuAction() throws OwnerException {
        return appRoleService.getRoleMenuMap();
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
    public List<AppRoleModel> getRoles(String roleName) throws OwnerException {
        return appRoleService.getRoles(getUserInfo(), roleName);
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserRole")
    @ApiOperation(value = "获取用户角色", notes = "获取用户角色")
    public List<Integer> getUserRole(Integer userId) {
        if (userId == null || userId <= 0) {
            return new ArrayList<>();
        }
        return appRoleService.getUserRoleIds(userId);
    }

    /**
     *
     * @param addRoleModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/setUserRole")
    @SystemLog(description = "设置用户角色",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置用户角色", notes = "设置用户角色")
    public Boolean setUserRole(@RequestBody @Validated AddUserRoleModel addRoleModel) throws  OwnerException{
        return appRoleService.setUserRole(addRoleModel, getUserInfo());
    }
}
