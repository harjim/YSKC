package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.AppRole;
import com.yskc.rs.models.role.AppRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色dao
 *
 * @author huronghua
 */
@Repository
public interface AppRoleDao extends BaseMapper<AppRole> {
    /**
     * 获取角色
     *
     * @param page
     * @param roleName
     * @return
     */
    List<AppRoleModel> getAppRoleList(@Param("page") Pagination page, @Param("roleName") String roleName);

    /**
     * 根据角色名称，获取角色条数
     *
     * @param roleName
     * @return
     */
    int getRoleCountByName(@Param("roleName") String roleName);

    /**
     * 获取公司角色
     *
     * @param page
     * @param roleName
     * @param companyId
     * @return
     */
    List<AppRoleModel> getRoles(@Param("roleName") String roleName, @Param("companyId") Integer companyId);


}
