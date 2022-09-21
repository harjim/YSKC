package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.menu.AppMenuModel;
import com.yskc.rs.models.menu.MenuInfo;
import com.yskc.rs.service.AppMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取菜单
     *
     * @param menuName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAppMenuList")
    @ApiOperation(value = "获取菜单", notes = "获取菜单", response = String.class)
    public List<AppMenuModel> getAppMenuList(String menuName, String status) throws OwnerException {
        int s = -1;
        if (status != null) {
            s = Integer.parseInt(status);
        }
        return appMenuService.getAppMenuList(menuName, s);
    }

    /**
     * 获取所有的菜单信息
     *
     * @return
     */
    @GetMapping("/getAppAllMenuList")
    @ApiOperation(value = "获取所有菜单信息", notes = "获取所有菜单信息", response = String.class)
    public List<MenuInfo> getAppAllMenuList() {
        return appMenuService.getAllAppMenus();
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加菜单", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @ApiOperation(value = "添加菜单", notes = "添加菜单", response = boolean.class)
    public boolean addMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.addMenu(userInfo.getId(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新菜单", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @ApiOperation(value = "更新菜单", notes = "更新菜单", response = boolean.class)
    public boolean updateMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.updateMenu(userInfo.getId(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除菜单", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @ApiOperation(value = "删除菜单", notes = "删除菜单", response = boolean.class)
    public boolean delMenu(@RequestBody @Validated AppMenuModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return appMenuService.delMenu(userInfo.getId(), model);
    }
}
