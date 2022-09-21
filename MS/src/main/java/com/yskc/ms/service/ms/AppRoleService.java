package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.role.*;

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
     * @return
     */
    PageModel<List<AppRoleModel>> getAppRoleList(QueryRoleModel query, UserInfo userInfo, DataPermModel perm);

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
    List<MenuInfo> getRoleMenuMap();

    /**
     * 获取角色菜单权限
     *
     * @param roleId
     * @return
     */
    List<RoleDataModel> getRoleMenuIds(Integer roleId);

    /**
     * 配置角色权限
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean setRole(Integer userId, SetRoleModel model) throws OwnerException;

    /**
     * 设置用户权限
     *
     * @param userId
     * @param model
     * @return
     */
    boolean setUserRole(Integer userId, UserRoleModel model) throws OwnerException;

    /**
     * 获取用户角色关联信息
     *
     * @param userId
     * @return
     * @throws OwnerException
     */
    UserRoleModel getUserRoleIds(Integer userId) throws OwnerException;

    /**
     * 批量设置角色
     *
     * @param id
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean setUserRoles(Integer id, UserRoleModel model) throws OwnerException;

    /**
     * 批量删除
     *
     * @param models
     * @return
     */
    boolean delBatch(List<RoleModel> models) throws OwnerException;

    /**
     * 根据角色Id获取数据
     *query
     * @return
     */
    PageModel<List<DeptUserInfo>> getUserByRoleId(QueryRoleManageModel query);

    /**
     * @param pageNo
     * @param pageSize
     * @param roleId
     * @return
     */
    PageModel<List<DeptUserInfo>> selectUserData(int pageNo, int pageSize, Integer roleId, String realName, String fullPath);

    /**
     * 批量删除角色下用户
     *
     * @param deptUserInfos
     * @param deptUserInfos
     * @return
     */
    boolean deleteBatchUser(List<DeptUserInfo> deptUserInfos);

    boolean updateServiceDept(UserDept userDept);
}
