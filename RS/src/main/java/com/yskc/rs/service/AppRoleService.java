package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.role.AddUserRoleModel;
import com.yskc.rs.models.role.MenuActionModel;
import com.yskc.rs.models.role.RoleModel;
import com.yskc.rs.models.role.AppRoleModel;

import java.util.List;

/**
 * 角色服务
 *
 * @author huronghua
 */
public interface AppRoleService {
    /**
     * 获取所有的角色
     *
     * @param pageNo
     * @param pageSize
     * @param roleName
     * @return
     */
    PageModel<List<AppRoleModel>> getAppRoleList(int pageNo, int pageSize, String roleName);

    /**
     * 添加角色
     *
     * @param userId
     * @param model
     * @return
     */
    boolean addRole(Integer userId, RoleModel model) throws OwnerException;

    /**
     * 删除角色
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delRole(Integer userId, RoleModel model) throws OwnerException;

    /**
     * 更新角色信息
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean updateRole(Integer userId, RoleModel model) throws OwnerException;

    /**
     * 获取菜单权限
     *
     * @return
     */
    List<MenuActionModel> getRoleMenuMap();

    /**
     * 获取公司角色
     *
     * @param userInfo
     * @param roleName
     * @return
     */
    List<AppRoleModel> getRoles(UserInfo userInfo, String roleName);

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<Integer> getUserRoleIds(Integer userId);

    /**
     * 设置用户角色
     *
     * @param addRoleModel
     * @param userInfo
     * @return
     */
    Boolean setUserRole(AddUserRoleModel addRoleModel, UserInfo userInfo) throws OwnerException;
}
