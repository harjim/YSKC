package com.yskc.ms.controller.ms;

/**
 * 用户接口
 *
 * @author huronghua
 */


import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.models.ModifyPasswordModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.login.LoginInfo;
import com.yskc.ms.models.mobile.CodeLoginModel;
import com.yskc.ms.models.user.*;
import com.yskc.ms.service.ms.AppMenuService;
import com.yskc.ms.service.ms.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "用户类接口", value = "用户类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppMenuService appMenuService;

    @NotLoginRequired
    @RequestMapping("dingLogin")
    @SystemLog(description = "钉钉登录")
    public void dingLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String code = req.getParameter("code");
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginTmpCode(code);
        String token = userService.ddLogin(loginInfo, getRequestIp());
        resp.sendRedirect("http://localhost:9000/user/info?token=" + token);
    }

    /**
     * 用户登录
     *
     * @param loginInfo
     * @return
     * @throws OwnerException
     */
    @NotLoginRequired
    @PostMapping("/login")
    @SystemLog(description = "用户登录")
    @ApiOperation(value = "用户登录", notes = "用户登录", response = String.class)
    public String login(@RequestBody @Validated LoginInfo loginInfo) throws OwnerException {
        if (loginInfo.getLoginType() == 0) {
            return userService.login(loginInfo, getRequestIp());
        } else {
            return userService.ddLogin(loginInfo, getRequestIp());
        }
    }

    @NotLoginRequired
    @PostMapping("/ddLogin")
    @SystemLog(description = "钉钉用户登录")
    @ApiOperation(value = "钉钉用户登录", notes = "钉钉用户登录", response = String.class)
    public String ddLogin(@RequestBody @Validated LoginInfo loginInfo) throws OwnerException {
        return userService.ddLogin(loginInfo, getRequestIp());
    }

    @NotLoginRequired
    @PostMapping("/loginByCode")
    @SystemLog(description = "钉钉电脑端登录")
    @ApiOperation(value = "钉钉电脑端免密登录", notes = "钉钉电脑端登录", response = String.class)
    public String loginByCode(@RequestBody @Validated CodeLoginModel codeLogin) throws OwnerException {
        return userService.loginByCode(codeLogin, "钉钉电脑端登录", getRequestIp(), PlatformEnum.PC);
    }

    @GetMapping("/info")
    @SystemLog(description = "获取用户信息")
    public UserAndMenuModel info() throws OwnerException {
        return appMenuService.getInfo(getUserInfo());
    }

    @PostMapping("/updatePassword")
    @SystemLog(description = "修改密码", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "修改密码", notes = "修改密码", response = Boolean.class)
    public Boolean updatePassword(@RequestBody @Validated ModifyPasswordModel pwdInfo) throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return userService.updatePassword(getToken(), userInfo.getId(), userInfo.getUserName(), pwdInfo);
    }

    @GetMapping("/queryUserData")
    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息")
    public PageModel<List<User>> queryUserData(int pageNo, int pageSize, String userName, String realName, String tel, Integer companyId) throws OwnerException {
        return userService.queryUserData(pageNo, pageSize, userName, realName, tel, companyId);
    }

    @PostMapping("/uploadDing")
    @SystemLog(description = "同步钉钉用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "同步钉钉用户", notes = "同步钉钉用户", response = Boolean.class)
    public Boolean uploadDing() throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return userService.uploadDing(userInfo);

    }

    @SystemLog(description = "退出登录", mode = SystemLog.SAVE_DB)
    @NotLoginRequired
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录", response = Boolean.class)
    public Boolean logout() throws OwnerException {
        return userService.logout(getToken());
    }


    @PostMapping("/addUser")
    @PermissionRequired(perms = "sys:user:add")
    @SystemLog(description = "插入用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "插入用户", notes = "插入用户", response = Boolean.class)
    public Boolean modifyUser(@RequestBody @Validated UserModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return userService.addUser(info.getId(), model);
    }

    @PostMapping("/updateUser")
    @PermissionRequired(perms = "sys:user:edit")
    @SystemLog(description = "更新用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新用户", notes = "更新用户", response = Boolean.class)
    public Boolean updateUser(@RequestBody @Validated UserModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return userService.updateUser(info.getId(), model);
    }

    @PostMapping("/resetPassword")
    @SystemLog(description = "重置用户密码", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码", response = Boolean.class)
    public Boolean resetPassword(@RequestBody @Validated ResetPasswordModel model) {
        return userService.resetPassword(model);
    }

    @GetMapping("/userForSelect")
    @ApiOperation(value = "查询用户下拉框(带部门)", notes = "查询用户下拉框(带部门)", response = List.class)
    public List<MiniUserModel> userForSelect(String realName, String fullPath, Boolean hasDept, Boolean noLeave) {
        return userService.userForSelect(realName, fullPath, hasDept, noLeave);
    }

    @GetMapping("/getUserList")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public PageModel<List<OwnerUserModel>> getUserList(QueryUserModel query) {
        return userService.getUserList(query);
    }

    @PostMapping("/setDept")
    @PermissionRequired(perms = "sys:user:edit")
    @SystemLog(description = "批量用户设置部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量用户设置部门", notes = "批量用户设置部门", response = Boolean.class)
    public Boolean setDept(@RequestBody @Validated UserDeptModel model) throws OwnerException {
        return userService.setDept(getUserInfo(), model);
    }

    @GetMapping("/editStatus")
    @PermissionRequired(perms = "sys:user:enabled,sys:user:disabled")
    @SystemLog(description = "禁用/启用用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "禁用/启用用户", notes = "禁用/启用用户", response = Boolean.class)
    public Boolean editStatus(Integer id, Integer status) throws OwnerException {
        return userService.editStatus(id, status);
    }

}
