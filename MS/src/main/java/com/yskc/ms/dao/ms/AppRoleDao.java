package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.AppRole;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.role.AppRoleModel;
import com.yskc.ms.models.role.QueryRoleManageModel;
import com.yskc.ms.models.role.QueryRoleModel;
import com.yskc.ms.models.role.UserDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色dao
 * @author huronghua
 */
@Repository
public interface AppRoleDao  extends BaseMapper<AppRole> {
    /**
     * 获取角色
     * @param page
     * @param query
     * @param perm
     * @return
     */
    List<AppRoleModel> getAppRoleList(@Param("page") Pagination page, @Param("query") QueryRoleModel query, @Param("perm") DataPermModel perm);

    /**
     * 根据角色名称，获取角色条数
     * @param roleName
     * @return
     */
    int getRoleCountByName(@Param("roleName") String roleName);

    /**
     * 根据角色获取用户
     * @param page
     * @param query
     * @return
     */
    List<DeptUserInfo> getUserByRoleId(Pagination page,  @Param("query") QueryRoleManageModel query);

    /**
     * 获取没有设置角色的用户
     * @param page
     * @param roleId
     * @return
     */
    List<DeptUserInfo> selectUserData(Pagination page,@Param("roleId")Integer roleId,@Param("realName")String realName,@Param("fullPath")String fullPath);

    /**
     * 删除角色下的用户
     * @param userIds
     * @param roleId
     * @return
     */
    boolean deleteBatchUser(@Param("userIds") List<Integer> userIds,@Param("roleId") Integer roleId);

    boolean updateServiceDept(@Param("userDept")UserDept userDept);

    /**
     * 获取角色列表
     * @param roleName
     * @return
     */
    List<AppRoleModel> getRoleList(@Param("roleName") String roleName);
}
