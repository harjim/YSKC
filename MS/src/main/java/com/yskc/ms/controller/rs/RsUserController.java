package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.User;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.user.ChildUserModel;
import com.yskc.ms.models.user.QueryRsUserModel;
import com.yskc.ms.service.rs.RsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-27 09:35
 * @Description: 账户管理Controller
 */
@Api(value = "账户管理类接口", tags = "账户管理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsUser")
public class RsUserController extends BaseController {
    @Autowired
    private RsUserService rsUserService;

    @GetMapping("/queryUserList")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @PermissionRequired(perms = "customer:all:platformAccount")
    public PageModel<List<User>> queryUserList(QueryRsUserModel query) throws OwnerException {
        return rsUserService.queryUserList(query);
    }

    @PostMapping("/addUser")
    @SystemLog(description = "添加用户端帐户", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:platformAccount")
    @ApiOperation(value = "添加用户端帐户", notes = "添加用户端帐户")
    public boolean addUser(@RequestBody @Validated ChildUserModel model) throws OwnerException {

        return rsUserService.addUser(getUserInfo(), model);
    }

    @PostMapping("/updateUser")
    @SystemLog(description = "更新用户端帐户", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:platformAccount")
    @ApiOperation(value = "更新用户端帐户", notes = "更新用户端帐户")
    public boolean updateUser(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        return rsUserService.updateUser(getUserInfo(), model);
    }

    @PostMapping("/delUser")
    @SystemLog(description = "删除用户端帐户", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:platformAccount")
    @ApiOperation(value = "删除用户端帐户", notes = "删除用户端帐户")
    public boolean delUser(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        return rsUserService.delUser(model);
    }

    @PostMapping("/updatePassword")
    @SystemLog(description = "重置用户端帐户密码", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:platformAccount")
    @ApiOperation(value = "重置用户端帐户密码", notes = "重置用户端帐户密码")
    public boolean updateUserPassword(@RequestBody @Validated ChildUserModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsUserService.updatePassword(userInfo, model);
    }


    @SystemLog(description = "检查用户名是否重复")
    @GetMapping("/checkuserName")
    @ApiOperation(value = "检查用户名是否重复", notes = "检查用户名是否重复")
    public Boolean checkuserName(String userName) throws OwnerException {
        return rsUserService.checkuserName(userName);
    }
}
