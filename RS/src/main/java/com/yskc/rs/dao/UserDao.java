package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.User;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.login.UserSession;
import com.yskc.rs.models.user.QueryUserListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao
 *
 * @author huronghua
 */
@Repository
public interface UserDao extends BaseMapper<User> {
    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    User getUserInfo(@Param("userName") String userName);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfo getUser(@Param("userId") Integer userId);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserSession getUserInfoByToken(@Param("token") String token);

    /**
     * 根据用户id获取用户权限菜单信息
     *
     * @param userId
     * @return
     */
    UserSession getUserInfoByUserId(@Param("userId") Integer userId);

    /**
     * 修改用户密码
     *
     * @param userId      用户Id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    Integer updatePassword(@Param("userId") Integer userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 获取所有user信息
     *
     * @param companyId
     * @param page
     * @param query
     * @return
     */
    List<User> queryUserList(@Param("companyId") int companyId, Pagination page, @Param("query") QueryUserListModel query);



    User registerCheckUser(@Param("userName") String userName);

    /**
     * 获取用户列表
     * @param userIds
     * @return
     */
    List<User> getUsers(@Param("userIds") List<Integer> userIds);
}
