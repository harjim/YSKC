package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.models.role.*;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.role.QueryRsRoleModel;
import com.yskc.ms.models.role.UserRoleModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-30 17:00
 * @Description: rs角色业务接口层
 */
public interface RsRoleService {

    /**
     * 获取角色列表
     *
     * @param query
     * @return
     */
    PageModel<List<RsRoleModel>> getList(QueryRsRoleModel query);

    /**
     * 添加角色
     *
     * @param rsRoleAddModel
     * @param userInfo
     * @return
     */
    boolean add(RsRoleAddModel rsRoleAddModel, UserInfo userInfo) throws OwnerException;

    /**
     * 根据公司名称获取公司
     *
     * @param companyName
     * @return
     */
    List<MiniModel> getCompany(String companyName);

    /**
     * 更新角色
     *
     * @param rsRoleAddModel
     * @param userInfo
     * @return
     */
    boolean update(RsRoleAddModel rsRoleAddModel, UserInfo userInfo) throws OwnerException;

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    boolean del(Integer id) throws OwnerException;

    /**
     * 通过当前用户获取菜单
     *
     * @param userId
     * @return
     */
    List<RsActionMenuModel> getMenuByUserId(Integer userId);

    /**
     * 配置角色功能
     *
     * @param rsRoleModel
     * @return
     */
    boolean setRole(SetRsRoleModel rsRoleModel, UserInfo info) throws OwnerException;

    /**
     * 获取角色功能
     *
     * @param roleId
     * @return
     */
    List<Integer> getRoleMenuIds(Integer roleId);

    /**
     * 设置公司角色，已使用的角色会重新插入
     *
     * @param companyRoleModel
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean setCompanyRole(CompanyRoleModel companyRoleModel, UserInfo userInfo) throws OwnerException;

    /**
     * 根据userId 获取角色
     *
     * @param userId
     * @param  companyId
     * @return
     */
    List<Integer> getRoleUserId(Integer userId,Integer companyId);

    /**
     * 设置用户角色
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean setUserRole(UserRoleModel model, UserInfo info) throws OwnerException;


    /**
     * 获取公司角色
     *
     * @param companyId
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageModel<List<RsRoleModel>> getRoles(Integer companyId, String roleName, Integer pageNo, Integer pageSize);


    PageModel<List<RsRoleModel>> queryCustomer(Integer roleId, String companyName, int pageNo, int pageSize);
}
