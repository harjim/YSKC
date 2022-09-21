package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.AppRoleMenu;
import com.yskc.ms.models.role.MenuPermModel;
import com.yskc.ms.models.role.RoleDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限菜单DAO
 */
@Repository
public interface AppRoleMenuDao  extends BaseMapper<AppRoleMenu> {
    /**
     * 获取角色菜单权限
     * @param roleId
     * @return
     */
    List<RoleDataModel> getRoleMenuIds(@Param("roleId") Integer roleId);

    /**
     * 根据角色ID，解除角色功能关联
     * @param roleIds
     * @return
     */
    Integer delRoleMenuByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 批量插入
     * @param attachmentTables
     */
    void insertByBatch(List<AppRoleMenu> attachmentTables);

    /**
     * 根据菜单ID删除角色菜单关联信息
     * @param menuId
     * @return
     */
    Integer delRoleMenuByMenuId(@Param("menuId") Integer menuId);

    /**
     * 获取角色权限，数据权限
     * @param roleId
     * @return
     */
    List<MenuPermModel> getPermData(@Param("roleId") Integer roleId);
}
