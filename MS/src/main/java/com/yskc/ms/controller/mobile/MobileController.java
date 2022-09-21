package com.yskc.ms.controller.mobile;

import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.models.mobile.CodeLoginModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.UserAndMenuModel;
import com.yskc.ms.service.ms.AppMenuService;
import com.yskc.ms.service.ms.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.mobile
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-28 08:20
 * @Description:
 */
@RestController
@RequestMapping("/api/mobile/index")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "移动端首页", value = "移动端首页")
public class MobileController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppMenuService appMenuService;

    @NotLoginRequired
    @PostMapping("/loginByCode")
    @ApiOperation(notes = "钉钉移动端登录", value = "钉钉移动端登录")
    public String loginByCode(@RequestBody CodeLoginModel codeLogin) throws OwnerException {
        return userService.loginByCode(codeLogin, "钉钉移动端登录", getRequestIp(), PlatformEnum.MOBILE);
    }

    @GetMapping("/getInfo")
    @ApiOperation(notes = "获取移动端用户信息", value = "获取移动端用户信息")
    public UserAndMenuModel getInfo() throws OwnerException {
        return appMenuService.getInfo(getUserInfo());
    }

    @GetMapping("/userForSelect")
    @ApiOperation(value = "查询用户下拉框(带部门)", notes = "查询用户下拉框(带部门)", response = List.class)
    public List<MiniUserModel> userForSelect(String realName, String fullPath, Boolean hasDept, Boolean noLeave) {
        return userService.userForSelect(realName, fullPath, hasDept,noLeave);
    }

    @GetMapping("/getDeptUser")
    @ApiOperation(value = "查询部门下用户", notes = "查询部门下用户")
    public List<MiniUserModel> getDeptUser(Integer deptId) {
        return userService.getDeptUser(deptId);
    }
}
