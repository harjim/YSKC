package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;
import com.yskc.ms.models.user.UserAndMenuModel;

import java.util.List;

/**
 * 菜单服务
 * @author huronghua
 */
public interface AppMenuService {
    /**
     * 获取全部菜单信息
     * @return
     */
    List<MenuInfo> getAllAppMenus(int type);
	List<AppMenuModel> getAppMenuList(String menuName, int status,int type);
	boolean addMenu(Integer id, AppMenuModel model);
	boolean updateMenu(Integer id, AppMenuModel model);
	boolean delMenu(Integer id, AppMenuModel model) throws OwnerException;
	List<MenuTree> queryAll(int type);

	/**
	 * 获取信息
	 * @param userInfo
	 * @return
	 */
	UserAndMenuModel getInfo(UserInfo userInfo);
}
