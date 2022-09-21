package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.User;
import com.yskc.rs.models.PwdMode;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyMember;
import com.yskc.rs.models.login.LoginInfo;
import com.yskc.rs.models.user.ChildUserModel;
import com.yskc.rs.models.user.QueryUserListModel;
import com.yskc.rs.models.user.RegisterModel;

import java.util.List;
import java.util.Map;

/**
 * 用户服务
 *
 * @author huronghu
 */
public interface UserService {
    /**
     * 获取用户信息
     *
     * @param loginInfo
     * @param requestIp
     * @return String
     * @throws OwnerException
     */
    Map login(LoginInfo loginInfo,String requestIp) throws OwnerException;

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    UserInfo getUserInfo(String token);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserInfo getUserInfoByToken(String token, String companyId) throws Exception;

    /**
     * 修改用户密码
     *
     * @param userId
     * @param pwdMode
     * @return
     */
    Boolean updatePassword(int userId, String userName, PwdMode pwdMode);

    /**
     * 用户登出
     *
     * @param token
     * @return
     */
    Boolean logout(String token);


    /**
     * 获取所用用户信息
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<User>> queryUserList(int companyId, QueryUserListModel query);

    /**
     * 添加子帐户
     *
     * @param info
     * @param model
     * @return
     */
    boolean addChildUser(UserInfo info, ChildUserModel model) throws OwnerException;

    /**
     * 修改子帐户
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateChildUser(UserInfo info, ChildUserModel model);

    /**
     * 删除子帐户
     *
     * @param model
     * @return
     */
    boolean delChilUser(ChildUserModel model);

    /**
     * 重置密码
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateChiluserPassword(UserInfo info, ChildUserModel model);

    /**
     * @param registerModel
     * @return
     * @throws OwnerException
     */

    Boolean register(RegisterModel registerModel) throws OwnerException;

    /**
     * 跳转登录
     *
     * @param loginInfo
     * @param requestIp
     * @return
     * @throws OwnerException
     */
    Map jumpLogin(LoginInfo loginInfo,String requestIp) throws OwnerException;

    /**
     * 检查用户是否存在
     *
     * @param userName
     * @param id
     * @return
     */
    Boolean checkuserName(String userName, Integer id);

    /**
     * @param userName
     * @return
     */
    Integer registerCheckUser(String userName);

    /**
     * 登录子公司
     * @param companyMember
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    String jumpChild(CompanyMember companyMember, UserInfo userInfo)throws OwnerException;
}
