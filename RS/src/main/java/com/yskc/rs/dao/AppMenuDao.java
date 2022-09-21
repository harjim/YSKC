package com.yskc.rs.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.AppMenu;
import com.yskc.rs.models.menu.AppMenuModel;
import com.yskc.rs.models.menu.MenuInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统菜单dao
 * @author huronghua
 */
@Repository
public interface AppMenuDao  extends BaseMapper<AppMenu> {
    /**
     * 获取菜单
     * @return
     */
    List<MenuInfo> getAppMenus();

	List<AppMenuModel> getAppMenuList(@Param("menuName")String menuName, @Param("status")int status);

	AppMenuModel getreeNode(@Param("cid")int cid);

	List<AppMenuModel> queryTreeNode(@Param("cid")int cid);
}
