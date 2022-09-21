package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsRole;
import com.yskc.ms.entity.rs.models.role.RsRoleModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.role.QueryRsRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:28:50
 */
@Repository
public interface RsRoleDao extends BaseMapper<RsRole> {

    /**
     * 获取rs角色列表
     *
     * @param page
     * @param query
     * @return
     */
    List<RsRoleModel> getList(Pagination page, @Param("query") QueryRsRoleModel query);

    /**
     * 通过公司名称获取数据公司
     *
     * @param companyName
     * @return
     */
    List<MiniModel> getCompany(@Param("companyName") String companyName);

    /**
     * @param companyId
     * @param roleName
     * @return
     */
    int checkRepeatCompanyRole(@Param("companyId") Integer companyId, @Param("roleName") String roleName);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<Integer> getRoleUserId(@Param("userId") Integer userId,@Param("companyId") Integer companyId);

    /**
     * 添加公司角色
     *
     * @param companyId
     * @param roleId
     */
    void addCompanyRole(@Param("companyId") Integer companyId, @Param("roleId") Integer roleId, @Param("userId") Integer userId, @Param("createTime") Date createTime);

    /**
     * 批量添加公司角色，
     *
     * @param companyId
     * @param roleIds
     * @param userId
     * @param createTime
     */
    void addBatchCompanyRole(@Param("companyId") Integer companyId, @Param("roleIds") Integer[] roleIds, @Param("userId") Integer userId, @Param("createTime") Date createTime);

    /**
     * 删除公司角色
     *
     * @param companyId
     * @param roleIds
     */
    void delCompanyRoleByCompanyId(@Param("companyId") Integer companyId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 获取用户角色
     *
     * @param page
     * @param roleName
     * @param companyId
     * @return
     */
    List<RsRoleModel> getRoles(@Param("page") Pagination page, @Param("roleName") String roleName, @Param("companyId") Integer companyId);


    List<RsRoleModel> queryMsUserName(@Param("userIds") List<Integer> userIds);

    List<RsRoleModel> queryCustomer(Pagination page, Integer roleId, String companyName);

    /**
     * 根据用户id获取角色名
     *
     * @param userId
     * @param companyIds
     * @return
     */
    List<RsRoleModel> getRoleName(@Param("userId") Integer userId,@Param("companyIds") List<Integer> companyIds);
}
