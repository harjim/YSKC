package com.yskc.rs.service.impl;

import com.yskc.rs.dao.AppMenuDao;
import com.yskc.rs.entity.AppMenu;
import com.yskc.rs.enums.MenuTypeEnum;
import com.yskc.rs.models.menu.AppMenuModel;
import com.yskc.rs.models.menu.MenuInfo;
import com.yskc.rs.service.AppMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author huronghua
 */
@Service
public class AppMenuServiceImpl implements AppMenuService {
    @Autowired
    private AppMenuDao appMenuDao;

    /**
     * 获取菜单
     *
     * @return
     */
    @Override
    public List<MenuInfo> getAppMenus() {
        return appMenuDao.getAppMenus().stream().filter(a -> a.getType() < MenuTypeEnum.BUTTON.getValue()).collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<MenuInfo> getAllAppMenus() {
        return appMenuDao.getAppMenus();
    }

    /**
     * @param menuName
     * @param status
     * @return
     */
    @Override
    public List<AppMenuModel> getAppMenuList(String menuName, int status) {
        if (!StringUtils.isEmpty(menuName)) {
            menuName = "%" + menuName + "%";
        }
        List<AppMenuModel> appMenuList = appMenuDao.getAppMenuList(menuName, status);
        List<AppMenuModel> testList = new ArrayList<AppMenuModel>();

        for (int i = 0; i < appMenuList.size(); i++) {
            if (appMenuList.get(i).getParentId() == -1) {
                AppMenuModel recursiveTree = recursiveTree(appMenuList.get(i).getId());
                testList.add(recursiveTree);
            }
        }

        return testList;
    }

    /**
     * @param cid
     * @return
     */
    public AppMenuModel recursiveTree(int cid) {
        //根据cid获取节点对象(SELECT * FROM tb_tree t WHERE t.cid=?)
        AppMenuModel node = appMenuDao.getreeNode(cid);
        //查询cid下的所有子节点(SELECT * FROM tb_tree t WHERE t.pid=?)
        List<AppMenuModel> appMenuTreeNodes = appMenuDao.queryTreeNode(cid);
        //遍历子节点
        for (AppMenuModel appMenu : appMenuTreeNodes) {
            //递归
            AppMenuModel n = recursiveTree(appMenu.getId());
            if (node.getChildren() == null) {
                List<AppMenuModel> childrenList = new ArrayList<AppMenuModel>();
                node.setChildren(childrenList);
            }
            node.getChildren().add(n);
        }

        return node;
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @Override
    public boolean addMenu(Integer id, AppMenuModel model) {
        // TODO Auto-generated method stub
        AppMenu parentModel = appMenuDao.selectById(model.getParentId());

        int level = parentModel.getLevel() + 1;

        AppMenu appMenu = new AppMenu();
        appMenu.setName(model.getName());
        appMenu.setParentId(model.getParentId());
        appMenu.setLevel(level);
        appMenu.setType(model.getType());
        appMenu.setUrl(model.getUrl());
        appMenu.setIcon(model.getIcon());
        appMenu.setPerms(model.getPerms());
        appMenu.setStatus(model.getStatus());
        appMenu.setSeq(model.getSeq());
        appMenu.setRemark(model.getRemark());
        return appMenuDao.insert(appMenu) > 0;
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @Override
    public boolean updateMenu(Integer id, AppMenuModel model) {
        // TODO Auto-generated method stub

        AppMenu parentModel = appMenuDao.selectById(model.getParentId());

        int level = parentModel.getLevel() + 1;

        AppMenu appMenu = new AppMenu();
        appMenu.setId(model.getId());
        appMenu.setName(model.getName());
        appMenu.setParentId(model.getParentId());
        appMenu.setLevel(level);
        appMenu.setType(model.getType());
        appMenu.setUrl(model.getUrl());
        appMenu.setIcon(model.getIcon());
        appMenu.setPerms(model.getPerms());
        appMenu.setStatus(model.getStatus());
        appMenu.setSeq(model.getSeq());
        appMenu.setRemark(model.getRemark());
        return appMenuDao.updateById(appMenu) > 0;
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @Override
    public boolean delMenu(Integer id, AppMenuModel model) {

        return appMenuDao.deleteById(model.getId()) > 0;
    }

}
