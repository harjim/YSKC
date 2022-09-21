package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.AppUserRole;
import com.yskc.ms.entity.ms.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户关联DAO
 *
 * @author huronghua
 */
@Repository
public interface AppUserRoleDao extends BaseMapper<AppUserRole> {
    /**
     * 获取角色关联的用户数
     *
     * @param roleId
     * @return
     */
    int getCountByRoleId(@Param("roleId") Integer roleId);

    /**
     * 删除用户权限关联数据
     *
     * @param userIds
     * @return
     */
    Integer deleteUserRole(@Param("userIds") List<Integer> userIds);

    /**
     * 批量插入
     *
     * @param appUserRoleList
     * @return
     */
    Integer addBatch(@Param("appUserRoleList") List<AppUserRole> appUserRoleList);

    List<Integer> getUserRoleIds(@Param("userId") Integer userId);

    void deleteByUserId(@Param("userId")Integer userId);

    /**
     * 获取角色关联的用户数
     *
     * @param roleIds
     * @return
     */
    List<Integer> getCountByListRoleId(@Param("roleIds") List<Integer> roleIds);

    /**
     * 通过用户获取角色
     * @param userIds
     * @return
     */
    List<AppUserRole> getByUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 获取角色所有用户
     * @param roleIds
     * @return
     */
    List<User> getByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
