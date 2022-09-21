package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.common.model.ProductMtype;
import com.yskc.rs.entity.AppUserRole;
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
     * 获取用户角色
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<Integer> getUserRoleIds(@Param("userId") Integer userId,@Param("companyId")Integer companyId);

    List<Integer> getUserRoleByMTypes(@Param("ProductMtypes") List<ProductMtype> ProductMtypes);
    /**
     * 删除用户角色
     * @param userId
     * @return
     */
    int delByUserId(@Param("userId")Integer userId);

    /**
     * 批量添加用户角色
     * @param userRoles
     * @return
     */
    int addBatch(@Param("userRoles") List<AppUserRole> userRoles);

    /**
     * 获取角色公司id
     * @param userId
     * @return
     */
    List<Integer> getCompanyIds(@Param("userId") Integer userId);
}
