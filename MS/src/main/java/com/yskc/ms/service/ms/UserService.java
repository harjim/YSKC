package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.models.ModifyPasswordModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.login.LoginInfo;
import com.yskc.ms.models.mobile.CodeLoginModel;
import com.yskc.ms.models.user.*;

import java.util.List;

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
     * @throws Exception
     */
    String login(LoginInfo loginInfo, String requestIp) throws OwnerException;

    /**
     * 钉钉登录
     *
     * @param loginInfo
     * @param requestIp
     * @return
     * @throws Exception
     */
    String ddLogin(LoginInfo loginInfo, String requestIp) throws OwnerException;

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
     * @throws Exception
     */
    UserInfo getUserInfoByToken(String token) throws Exception;

    /**
     * 修改用户密码
     *
     * @param userId
     * @param pwdMode
     * @return
     */
    Boolean updatePassword(String token, int userId, String userName, ModifyPasswordModel pwdMode);

    /**
     * 添加用户
     *
     * @param currentUserId
     * @param model
     * @return
     */
    Boolean addUser(Integer currentUserId, UserModel model) throws OwnerException;


    /**
     * 更新用户
     *
     * @param currentUserId
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean updateUser(Integer currentUserId, UserModel model) throws OwnerException;

    /**
     * 重置密码
     *
     * @param rpm
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月8日 下午4:40:05
     */
    Boolean resetPassword(ResetPasswordModel rpm);


    /**
     * 获取所有用户信息
     *
     * @param pageNo
     * @param pageSize
     * @param userName
     * @param realName
     * @param tel
     * @param companyId
     * @return
     * @throws OwnerException
     */
    PageModel<List<User>> queryUserData(int pageNo, int pageSize, String userName, String realName, String tel, Integer companyId) throws OwnerException;

    Boolean uploadDing(UserInfo userInfo) throws OwnerException;

    /**
     * 登出
     *
     * @param token
     * @return
     */
    Boolean logout(String token);

    /**
     * 查询用户下拉框(带部门)
     *
     * @param realName
     * @param fullPath
     * @param hasDept
     * @return
     */
    List<MiniUserModel> userForSelect(String realName, String fullPath, Boolean hasDept, Boolean noLeave);

    /**
     * 查询用户下拉框(带部门)条件可为空
     *
     * @param realName
     * @param fullPath
     * @param hasDept
     * @return
     */
    List<MiniUserModel> userForDept(String realName, String fullPath, Boolean hasDept, Boolean noLeave);

    /**
     * 查询部门下用户
     *
     * @param deptId
     * @return
     */
    List<MiniUserModel> getDeptUser(Integer deptId);

    /**
     * 获取用户列表
     *
     * @param query
     * @return
     */
    PageModel<List<OwnerUserModel>> getUserList(QueryUserModel query);

    Boolean setDept(UserInfo userInfo, UserDeptModel model);

    /**
     * 禁用/启用用户
     *
     * @param id
     * @return
     */
    Boolean editStatus(Integer id, Integer status) throws OwnerException;

    /**
     * 通过code登录
     *
     * @param codeLogin
     * @param loginType
     * @param requestIp
     * @param desc
     * @return
     * @throws OwnerException
     */
    String loginByCode(CodeLoginModel codeLogin, String desc, String requestIp, PlatformEnum loginType)throws OwnerException;
}
