package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsUserRole;
import com.yskc.ms.entity.rs.models.role.RsActionMenuModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:29:02
 */
@Repository
public interface RsUserRoleDao extends BaseMapper<RsUserRole> {

    /**
     * 查询关联角色的用户个数
     *
     * @param roleId
     * @return
     */
    int getByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    List<RsActionMenuModel> getUserMenu(@Param("userId") Integer userId);

    /**
     * 删除用户角色(按页角色删除)
     *
     * @param userId
     * @param pageRoleIds
     * @param companyIds
     * @return
     */
    int delByUserId(@Param("userId") Integer userId,@Param("companyIds") List<Integer> companyIds,@Param("pageRoleIds") List<Integer> pageRoleIds);

    /**
     * 批量添加用户角色
     *
     * @param userRoles
     * @return
     */
    int addBatch(@Param("userRoles") List<RsUserRole> userRoles);

    /**
     * 通过角色id获取已绑定人员的角色id
     *
     * @param roleIds
     * @param companyId
     * @return
     */
    List<Integer> getUserRoleIds(@Param("roleIds") List<Integer> roleIds,@Param("companyId") Integer companyId);

    /**
     * 获取集团用户子公司角色
     * @param groupId
     * @param companyIds
     * @return
     */
    List<Integer> getGroupUserRole(@Param("groupId") Integer groupId,@Param("companyIds") List<Integer> companyIds);
}
