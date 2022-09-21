package com.yskc.rs.service;

import com.yskc.rs.models.menu.AppMenuModel;
import com.yskc.rs.models.menu.MenuInfo;

import java.util.List;

/**
 * 菜单服务
 * @author huronghua
 */
public interface AppMenuService {
    /**
     * 获取菜单
     * @return
     */
    List<MenuInfo> getAppMenus();
    /**
     * 获取全部菜单信息
     * @return
     */
    List<MenuInfo> getAllAppMenus();

    /**
     *
     * @param menuName
     * @param status
     * @return
     */
	List<AppMenuModel> getAppMenuList(String menuName, int status);

    /**
     *
     * @param id
     * @param model
     * @return
     */
	boolean addMenu(Integer id, AppMenuModel model);

    /**
     *
     * @param id
     * @param model
     * @return
     */
	boolean updateMenu(Integer id, AppMenuModel model);

    /**
     *
     * @param id
     * @param model
     * @return
     */
	boolean delMenu(Integer id, AppMenuModel model);
}
