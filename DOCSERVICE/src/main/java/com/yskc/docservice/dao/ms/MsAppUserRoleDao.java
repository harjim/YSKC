package com.yskc.docservice.dao.ms;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.ms.Dept;
import com.yskc.docservice.entity.ms.MsAppUserRole;
import com.yskc.docservice.models.ms.login.MenuPermModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户关联DAO
 *
 * @author huronghua
 */
@Repository
public interface MsAppUserRoleDao extends BaseMapper<MsAppUserRole> {


    List<Integer> getUserRoleIds(@Param("userId") Integer userId);

    /**
     * 通过用户获取角色
     * @param userIds
     * @return
     */
    List<MsAppUserRole> getByUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 获取部门信息
     * @param ids
     * @return
     */
    List<Dept> getByDeptIds(@Param("ids") List<Integer> ids);

    /**
     * 获取超级管理员权限
     * @return
     */
    List<MenuPermModel> getAdminPerms();

    /**
     * 获取角色权限，数据权限
     * @param roleId
     * @return
     */
    List<MenuPermModel> getPermData(@Param("roleId") Integer roleId);
}
