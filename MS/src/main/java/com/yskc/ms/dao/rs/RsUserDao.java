package com.yskc.ms.dao.rs;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.User;
import com.yskc.ms.models.user.QueryRsUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao
 *
 * @author huronghua
 */
@Repository
public interface RsUserDao extends BaseMapper<User> {
    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    User getUserInfo(@Param("userName") String userName);

    /**
     * 根据公司获取主账户
     *
     * @param companyId
     * @return
     */
    User getUserByCompanyId(@Param("companyId") Integer companyId);


    /**
     * 获取所有user信息
     *
     * @param query
     * @param page
     * @return
     */
    List<User> queryUserList(Pagination page, @Param("query") QueryRsUserModel query);


    /**
     * 查询用户名唯一性
     *
     * @param companyId
     * @param userName
     * @return
     */
    User getUserByTerm(@Param("companyId") int companyId, @Param("userName") String userName);

    User getUserInfoByCompanyId(String userName, Integer companyId);

    void updateByCompanyId(@Param("companyId") Integer companyId, @Param("userName") String userName);
}
