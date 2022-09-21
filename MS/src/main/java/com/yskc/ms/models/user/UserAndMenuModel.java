package com.yskc.ms.models.user;

import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.menu.MenuInfo;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.user
 * @Author: zhangdingfu
 * @CreateTime: 2022-06-28 09:35
 * @Description: 用户及菜单信息model
 */
public class UserAndMenuModel {

    private UserInfo user;

    private List<MenuInfo> menus;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<MenuInfo> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuInfo> menus) {
        this.menus = menus;
    }
}
