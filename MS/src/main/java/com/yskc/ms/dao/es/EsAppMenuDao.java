package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.es.EsMenu;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/10/27 9:49
 * @Description:
 * @author: hsx
 */
@Repository
public interface EsAppMenuDao extends BaseMapper<EsMenu> {

    /**
     * 获取es人才端菜单
     * @param menuName
     * @param status
     * @return
     */
    List<AppMenuModel> getAppMenuList(@Param("menuName")String menuName, @Param("status")int status);

    /**
     * 获取菜单
     * @return
     */
    List<MenuInfo> getAppMenus();

    List<MenuTree> queryAll();
}
