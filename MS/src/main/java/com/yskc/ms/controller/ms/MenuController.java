package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;
import com.yskc.ms.service.ms.AppMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.menu.AppMenuModel;

/**
 * 菜单类接口
 *
 * @author huronghua
 */
@Api(tags = "菜单类接口", value = "菜单类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController {
    @Autowired
    private AppMenuService appMenuService;

    @GetMapping("/tree")
    @ApiOperation(value = "菜单树", notes = "菜单树", response = List.class)
    public List<MenuTree> tree(Integer menuManageType) {
        return MenuTree.getTree(appMenuService.queryAll(menuManageType));
    }

    /**
     * 获取菜单
     *
     * @param menuName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAppMenuList")
    @PermissionRequired(perms = "sys:menu:search")
    @ApiOperation(value = "获取菜单", notes = "获取菜单", response = String.class)
    public List<AppMenuModel> getAppMenuList(String menuName, String status, int menuManageType) throws OwnerException {
        int s = -1;
        if (status != null) {
            s = Integer.parseInt(status);
        }
        return appMenuService.getAppMenuList(menuName, s, menuManageType);
    }

    /**
     * 获取所有的菜单信息
     *
     * @return
     */
    @GetMapping("/getAppAllMenuList")
    @ApiOperation(value = "获取所有菜单信息", notes = "获取所有菜单信息", response = String.class)
    public List<MenuInfo> getAppAllMenuList(int menuManageType) {
        return appMenuService.getAllAppMenus(menuManageType);
    }

    @PostMapping("/add")
    @PermissionRequired(perms = "sys:menu:add")
    @ApiOperation(value = "添加菜单", notes = "添加菜单", response = boolean.class)
    public boolean addMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.addMenu(userInfo.getId(), model);
    }

    @PostMapping("/update")
    @PermissionRequired(perms = "sys:menu:edit")
    @ApiOperation(value = "修改菜单", notes = "修改菜单", response = boolean.class)
    public boolean updateMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.updateMenu(userInfo.getId(), model);
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "sys:menu:del")
    @ApiOperation(value = "删除菜单", notes = "删除菜单", response = boolean.class)
    public boolean delMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.delMenu(userInfo.getId(), model);
    }
}
