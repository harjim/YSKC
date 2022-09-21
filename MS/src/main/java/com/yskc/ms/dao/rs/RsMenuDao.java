package com.yskc.ms.dao.rs;

import com.yskc.ms.entity.rs.RsMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:22:19
 */
@Repository
public interface RsMenuDao extends BaseMapper<RsMenu> {
    /**
     * 获取菜单
     * @return
     */
    List<MenuInfo> getAppMenus();

    List<AppMenuModel> getAppMenuList(@Param("menuName") String menuName, @Param("status") int status);

    AppMenuModel getreeNode(@Param("cid") int cid);

    List<AppMenuModel> queryTreeNode(@Param("cid") int cid);

    List<MenuTree> queryAll();
}
