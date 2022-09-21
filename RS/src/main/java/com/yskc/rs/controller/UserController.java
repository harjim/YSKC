package com.yskc.rs.controller;

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
import com.yskc.rs.entity.User;
import com.yskc.rs.enums.CompanyGroupEnum;
import com.yskc.rs.models.PwdMode;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyMember;
import com.yskc.rs.models.login.LoginInfo;
import com.yskc.rs.models.menu.MenuInfo;
import com.yskc.rs.models.user.ChildUserModel;
import com.yskc.rs.models.user.QueryUserListModel;
import com.yskc.rs.models.user.RegisterModel;
import com.yskc.rs.service.AppMenuService;
import com.yskc.rs.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huronghua
 */
@Api(tags = "用户类接口", value = "用户类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppMenuService appMenuService;


    /**
     * 用户登录
     *
     * @param loginInfo
     * @return
     * @throws OwnerException
     */
    @NotLoginRequired
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录", response = Map.class)
    public Map login(@RequestBody @Validated LoginInfo loginInfo) throws OwnerException {
        String requestIp = getRequestIp();
        if (loginInfo.getIsAdmin()) {
            return userService.jumpLogin(loginInfo,requestIp);
        } else {
            return userService.login(loginInfo,requestIp);
        }
    }

    /**
     * 登录子公司
     *
     * @param companyMember
     * @return
     * @throws OwnerException
     */
    @PostMapping("/jumpChild")
    @SystemLog(description = "登录子公司", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "登录子公司", notes = "登录子公司", response = Map.class)
    @PermissionRequired(perms = "group:companyRdSummary:jump")
    public String jumpChild(@RequestBody @Validated CompanyMember companyMember) throws OwnerException {
        return userService.jumpChild(companyMember, getUserInfo());
    }

    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/info")
    public Map<String, Object> info() throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        Map resultMap = new HashMap();
        List<MenuInfo> menuList = appMenuService.getAppMenus();
        Map<Integer, Boolean> menuMap = new HashMap<>();
        userInfo.getPermDataMap().values().forEach(item -> {
            menuMap.put(item.getMenuId(), true);
            menuMap.put(item.getParentId(), true);
        });
        // RS用户，属于集团公司，获取集团菜单
        if (0 == userInfo.getUserSource() && CompanyGroupEnum.isGroup(userInfo.getCompanyType()) && userInfo.getCompanyId().equals(userInfo.getGroupId())) {
            menuList = menuList.stream().filter(a -> menuMap.containsKey(a.getId())).collect(Collectors.toList());
        } else {
            menuList = menuList.stream().filter(a -> a.getMenuType() == 0 && menuMap.containsKey(a.getId())).collect(Collectors.toList());
        }
        resultMap.put("user", userInfo);
        resultMap.put("menus", menuList);
        return resultMap;
    }

    /**
     * 获取登录用户信息
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息", response = UserInfo.class)
    public UserInfo getInfo() throws OwnerException {
        return getUserInfo();
    }

    /**
     * 修改密码
     *
     * @param pwdInfo
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updatePassword")
    @SystemLog(description = "修改密码", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "修改密码", notes = "修改密码", response = Boolean.class)
    public Boolean updatePassword(@RequestBody @Validated PwdMode pwdInfo) throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return userService.updatePassword(userInfo.getId(), userInfo.getUserName(), pwdInfo);
    }

    /**
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "退出登录", mode = SystemLog.SAVE_DB)
    @NotLoginRequired
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "修改密码", response = Boolean.class)
    public Boolean logout() throws OwnerException {
        return userService.logout(getToken());
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryUserList")
    @PermissionRequired(perms = "company:user:search")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public PageModel<List<User>> queryUserList(QueryUserListModel query) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return userService.queryUserList(userInfo.getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除子帐户", mode = SystemLog.SAVE_DB)
    @PostMapping("/delChilduser")
    @PermissionRequired(perms = "company:user:del")
    @ApiOperation(value = "删除子帐户", notes = "删除子帐户")
    public boolean delChilduser(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return userService.delChilUser(model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新子帐户", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateChilduser")
    @PermissionRequired(perms = "company:user:edit")
    @ApiOperation(value = "更新子帐户", notes = "更新子帐户")
    public boolean updateChilduser(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return userService.updateChildUser(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加子帐户", mode = SystemLog.SAVE_DB)
    @PostMapping("/addChilduser")
    @PermissionRequired(perms = "company:user:add")
    @ApiOperation(value = "添加子帐户", notes = "添加子帐户")
    public boolean addChilduser(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return userService.addChildUser(userInfo, model);
    }

    /**
     * @param userName
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "检查用户名是否重复")
    @GetMapping("/checkuserName")
    @PermissionRequired(perms = "company:user:search")
    @ApiOperation(value = "检查用户名是否重复", notes = "检查用户名是否重复")
    public Boolean checkuserName(String userName, Integer id) throws OwnerException {
        return userService.checkuserName(userName, id);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "重置子帐户密码", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateChilduserPassword")
    @PermissionRequired(perms = "company:user:resettingPassword")
    @ApiOperation(value = "重置子帐户密码", notes = "重置子帐户密码")
    public boolean updateChilduserPassword(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return userService.updateChiluserPassword(userInfo, model);
    }

    /**
     * 注册账号
     *
     * @param registerModel
     * @return
     * @throws OwnerException
     */
    @NotLoginRequired
    @PostMapping("/register")
    @ApiOperation(value = "注册账号", notes = "注册账号", response = Boolean.class)
    public Boolean register(@RequestBody @Validated RegisterModel registerModel) throws OwnerException {
        return userService.register(registerModel);
    }

    /**
     * @param userName
     * @return
     * @throws OwnerException
     */
    @NotLoginRequired
    @SystemLog(description = "注册检查用户名是否重复", mode = SystemLog.SAVE_DB)
    @GetMapping("/registerCheckUser")
    @ApiOperation(value = "注册检查用户名是否重复", notes = "注册检查用户名是否重复")
    public Integer registerCheckUser(String userName) throws OwnerException {
        return userService.registerCheckUser(userName);
    }
}
