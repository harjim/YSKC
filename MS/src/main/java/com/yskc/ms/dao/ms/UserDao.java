package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.login.UserSession;
import com.yskc.ms.models.role.MenuPermModel;
import com.yskc.ms.models.serviceApply.ServiceLeaderModel;
import com.yskc.ms.models.serviceApply.ServiceMemberModel;
import com.yskc.ms.models.user.*;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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
     * 获取所有权限
     *
     * @return
     */
    List<MenuPermModel> getAdminPerms();

    /**
     * 修改用户密码
     *
     * @param userId      用户Id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return
     */
    Integer updatePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 重置密码
     *
     * @param userId
     * @param password
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月8日 下午4:52:45
     */
    Integer resetPassword(@Param("userId") Integer userId, @Param("password") String password);

    /**
     * 获取用户信息
     *
     * @param page
     * @param userName
     * @param realName
     * @param tel
     * @param existIds
     * @return
     */
    List<User> queryUserData(@Param("page") Pagination page,
                             @Param("userName") String userName,
                             @Param("realName") String realName,
                             @Param("tel") String tel,
                             @Param("existIds") List<Integer> existIds);

    List<ServiceLeaderModel> getDeptByRole(@Param("paths")List<Integer> paths,
                                           @Param("finaMan")String finaMan, @Param("techMan")String techMan,
                                           @Param("finaDir")String finaDir, @Param("techDir")String techDir);

    String getFullPath(@Param("userIds")List<Integer> userIds);

    List<ServiceMemberModel> getMemberList(@Param("userName")String userName,@Param("fullPath")String fullPath,
                                           @Param("paths")List<Integer> paths);

    List<User> queryUserAll();


    @Options(useGeneratedKeys = true)
    int insertBatch(List<User> userList);

    @Options(useGeneratedKeys = true)
    int insertOrUpdate(List<User> userList);

    /**
     * 查询用户下拉框 带部门
     *
     * @param realName
     * @param fullPath
     * @param noLeave
     * @return
     */
    List<MiniUserModel> userForSelect(@Param("realName") String realName, @Param("fullPath") String fullPath,
                                      @Param("noLeave") Boolean noLeave);

    /**
     * 查询用户下拉框 带部门 无个数限制
     *
     * @param realName
     * @param fullPath
     * @param noLeave
     * @return
     */
    List<MiniUserModel> userForDept(@Param("realName") String realName, @Param("fullPath") String fullPath,
                                      @Param("noLeave") Boolean noLeave);

    /**
     * 获取用户列表
     *
     * @param page
     * @param query
     * @return
     */
    List<OwnerUserModel> getUserList(@Param("page") Pagination page, @Param("query") QueryUserModel query);

    /**
     * 通过钉钉unionId 获取用户
     *
     * @param unionid
     * @return
     */
    User getByUnionId(@Param("unionid") String unionid);

    /**
     * 更新token
     *
     * @param id
     * @param token
     * @return
     */
    Integer updateToken(@Param("id") Integer id, @Param("token") String token);

    void updateStatus(@Param("userIds") List<Integer> userIds);

    void updateUsers(@Param("updateUserList") List<User> updateUserList);

    /**
     * 获取用户名
     *
     * @param userIds
     * @return
     */
    List<MiniUserModel> getUsers(@Param("userIds") Set<Integer> userIds);

    /**
     * 获取操作日志用户列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<OperationLogUserModel> getOperationLogUserList(@Param("page") Pagination page, @Param("query") QueryUserListModel query, @Param("perm") DataPermModel dataPerm);
}
