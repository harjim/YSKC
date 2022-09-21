package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.es.EsAppMenuDao;
import com.yskc.ms.dao.ms.AppMenuDao;
import com.yskc.ms.dao.ms.AppRoleMenuDao;
import com.yskc.ms.dao.rs.RsMenuDao;
import com.yskc.ms.entity.es.EsMenu;
import com.yskc.ms.entity.ms.AppMenu;
import com.yskc.ms.entity.rs.RsMenu;
import com.yskc.ms.enums.MenuTypeEnum;
import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.menu.AppMenuModel;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.menu.MenuTree;
import com.yskc.ms.models.role.MenuPermModel;
import com.yskc.ms.models.user.UserAndMenuModel;
import com.yskc.ms.service.ms.AppMenuService;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private AppRoleMenuDao appRoleMenuDao;
    @Autowired
    private RsMenuDao rsAppMenuDao;
    @Autowired
    private EsAppMenuDao esAppMenuDao;
    @Autowired
    private MsConfig msConfig;

    @Override
    public List<MenuInfo> getAllAppMenus(int type) {
        if (type == 1) {
            return appMenuDao.getAppMenus();
        } else if (type == 2) {
            return rsAppMenuDao.getAppMenus();
        } else {
            return esAppMenuDao.getAppMenus();
        }

    }

    @Override
    public List<AppMenuModel> getAppMenuList(String menuName, int status, int type) {
        if (menuName != null && menuName != "") {
            menuName = "%" + menuName + "%";
        }
        List<AppMenuModel> appMenuList = new ArrayList<>();
        if (type == 1) {
            appMenuList = appMenuDao.getAppMenuList(menuName, status);
        } else if (type == 2) {
            appMenuList = rsAppMenuDao.getAppMenuList(menuName, status);
        } else {
            appMenuList = esAppMenuDao.getAppMenuList(menuName, status);
        }

        List<AppMenuModel> testList = new ArrayList<AppMenuModel>();
        if (appMenuList.size() > 0) {
            testList = getTree(appMenuList, appMenuList.get(0).getParentId());
        }

        return testList;
    }

    public static List<AppMenuModel> getTree(List<AppMenuModel> tree, int pid) {
        return getTrees(tree, pid);
    }

    /**
     * 部门树递归
     *
     * @param tree
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月4日 下午4:55:26
     */
    public static List<AppMenuModel> getTrees(List<AppMenuModel> tree, int parentId) {
        List<AppMenuModel> dList = new ArrayList<AppMenuModel>();
        for (AppMenuModel d : tree) {
            if (d.getParentId().equals(parentId)) {
                List<AppMenuModel> b = getTree(tree, d.getId());
                if (b.size() > 0) {
                    d.setChildren(b);
                }
                dList.add(d);
            }
        }
        return dList;
    }

    public AppMenuModel recursiveTree(int cid) {
        AppMenuModel node = appMenuDao.getreeNode(cid);
        List<AppMenuModel> appMenuTreeNodes = appMenuDao.queryTreeNode(cid);
        // 遍历子节点
        for (AppMenuModel appMenu : appMenuTreeNodes) {
            // 递归
            AppMenuModel n = recursiveTree(appMenu.getId());
            if (node.getChildren() == null) {
                List<AppMenuModel> childrenList = new ArrayList<AppMenuModel>();
                node.setChildren(childrenList);
            }
            node.getChildren().add(n);
        }

        return node;
    }

    @Override
    public boolean addMenu(Integer id, AppMenuModel model) {
        AppMenu appMenu = new AppMenu();
        AppMenu parentModel = new AppMenu();
        RsMenu rsMenu = new RsMenu();
        EsMenu esMenu = new EsMenu();
        int level = 0;
        if (model.getParentId() == -1) {
            parentModel.setLevel(0);
            parentModel.setFullPath("");
        } else {
            if (model.getMenuManageType() == 1) {
                parentModel = appMenuDao.selectById(model.getParentId());
                level = parentModel.getLevel() + 1;
            } else if (model.getMenuManageType() == 2) {
                rsMenu = rsAppMenuDao.selectById(model.getParentId());
                level = rsMenu.getLevel() + 1;
            } else {
                esMenu = esAppMenuDao.selectById(model.getParentId());
                level = esMenu.getLevel() + 1;
            }
        }
        appMenu.setName(model.getName());
        appMenu.setParentId(model.getParentId());
        appMenu.setLevel(level);
        appMenu.setType(model.getType());
        appMenu.setUrl(model.getUrl());
        appMenu.setIcon(model.getIcon());
        appMenu.setPerms(model.getPerms());
        appMenu.setStatus(model.getStatus());
        appMenu.setPlatform(model.getPlatform());
        appMenu.setSeq(model.getSeq());
        appMenu.setRemark(model.getRemark());
        Integer menuId;
        BeanUtil.copyProperties(appMenu, rsMenu);
        BeanUtil.copyProperties(appMenu, esMenu);
        if (model.getMenuManageType() == 1) {
            menuId = appMenuDao.insert(appMenu);
        } else if (model.getMenuManageType() == 2) {
            rsMenu.setHasYearSelect(model.isHasYearSelect());
            rsMenu.setMenuType(model.getMenuType());
            menuId = rsAppMenuDao.insert(rsMenu);
        } else {
            menuId = esAppMenuDao.insert(esMenu);
        }
        if (model.getParentId() == -2) {
            appMenu.setFullPath(menuId + "");
        } else {
            appMenu.setFullPath(parentModel.getFullPath() + ":" + menuId);
        }
        if (model.getMenuManageType() == 1) {
            return appMenuDao.updateById(appMenu) > 0;
        } else if (model.getMenuManageType() == 2) {
            return rsAppMenuDao.updateById(rsMenu) > 0;
        } else {
            return esAppMenuDao.updateById(esMenu) > 0;
        }

    }

    @Override
    public boolean updateMenu(Integer id, AppMenuModel model) {
        int level = 0;
        if (model.getParentId() != -1) {
            if (model.getMenuManageType() == 1) {
                AppMenu parentModel = appMenuDao.selectById(model.getParentId());
                level = parentModel.getLevel() + 1;
            } else if (model.getMenuManageType() == 2) {
                RsMenu rsAppMenu = rsAppMenuDao.selectById(model.getParentId());
                level = rsAppMenu.getLevel() + 1;
            } else {
                EsMenu esAppMenu = esAppMenuDao.selectById(model.getParentId());
                level = esAppMenu.getLevel() + 1;
            }
        }
        AppMenu appMenu = new AppMenu();
        appMenu.setId(model.getId());
        appMenu.setName(model.getName());
        appMenu.setParentId(model.getParentId());
        appMenu.setLevel(level);
        appMenu.setType(model.getType());
        appMenu.setUrl(model.getUrl());
        appMenu.setIcon(model.getIcon());
        appMenu.setPlatform(model.getPlatform());
        appMenu.setPerms(model.getPerms());
        appMenu.setStatus(model.getStatus());
        appMenu.setSeq(model.getSeq());
        appMenu.setRemark(model.getRemark());
        if (model.getMenuManageType() == 1) {
            return appMenuDao.updateById(appMenu) > 0;
        } else if (model.getMenuManageType() == 2) {
            RsMenu rsMenu = new RsMenu();
            BeanUtil.copyProperties(appMenu, rsMenu);
            rsMenu.setHasYearSelect(model.isHasYearSelect());
            rsMenu.setMenuType(model.getMenuType());
            return rsAppMenuDao.updateById(rsMenu) > 0;
        } else {
            EsMenu esMenu = new EsMenu();
            BeanUtil.copyProperties(appMenu, esMenu);
            return esAppMenuDao.updateById(esMenu) > 0;
        }
    }

    @Override
    public boolean delMenu(Integer id, AppMenuModel model) throws OwnerException {
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {

            if (model.getMenuManageType() == 1) {
                appRoleMenuDao.delRoleMenuByMenuId(model.getId());
                appMenuDao.deleteById(model.getId());
            } else if (model.getMenuManageType() == 2) {
                appRoleMenuDao.delRoleMenuByMenuId(model.getId());
                rsAppMenuDao.deleteById(model.getId());
            } else {
                appRoleMenuDao.delRoleMenuByMenuId(model.getId());
                esAppMenuDao.deleteById(model.getId());
            }
            TransactionUtils.commit(DataSourceEnum.MS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public List<MenuTree> queryAll(int type) {
        List<MenuTree> queryAll = new ArrayList<>();
        if (type == 1) {
            queryAll = appMenuDao.queryAll();
        } else if (type == 2) {
            queryAll = rsAppMenuDao.queryAll();
        } else {
            queryAll = esAppMenuDao.queryAll();
        }

        MenuTree menuTree = new MenuTree();
        menuTree.setKey(-1);
        menuTree.setTitle("根目录");
        menuTree.setParentId(-2);
        queryAll.add(menuTree);
        return queryAll;
    }

    @Override
    public UserAndMenuModel getInfo(UserInfo userInfo) {
        UserAndMenuModel result = new UserAndMenuModel();
        Boolean mobile = PlatformEnum.isMobile(userInfo.getPlatform());
        List<MenuInfo> menuList = appMenuDao.getPlatformMenu(mobile ? MenuTypeEnum.MENU.getValue() : MenuTypeEnum.BUTTON.getValue(), userInfo.getPlatform(), mobile);
        if (!msConfig.getLogin().getAdminName().equals(userInfo.getUserName())) {
            Map<String, MenuPermModel> permMap = userInfo.getPermDataMap();
            menuList = menuList.stream().filter(a -> permMap.containsKey(a.getPerms())).collect(Collectors.toList());
        }
        result.setUser(userInfo);
        result.setMenus(menuList);
        return result;
    }

}
