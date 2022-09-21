package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.AppMenu;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单dao
 *
 * @author huronghua
 */
@Repository
public interface AppMenuDao extends BaseMapper<AppMenu> {
    /**
     * 获取菜单
     *
     * @return
     */
    List<MenuInfo> getAppMenus();

    List<AppMenuModel> getAppMenuList(@Param("menuName") String menuName, @Param("status") int status);

    AppMenuModel getreeNode(@Param("cid") int cid);

    List queryTreeNode(@Param("cid") int cid);

    List<MenuTree> queryAll();

    /**
     * 通过权限获取menuId
     *
     * @param perms
     * @return
     */
    Integer getByPerms(@Param("perms") String perms);

    /**
     * 获取平台菜单
     *
     * @param btnType（菜单类型）
     * @param platform 平台
     * @param mobile 是否移动端
     * @return
     */
    List<MenuInfo> getPlatformMenu(@Param("btnType") int btnType, @Param("platform") Integer platform,@Param("mobile") Boolean mobile);
}
