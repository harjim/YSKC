package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.AppMenuDao;
import com.yskc.rs.dao.AppRoleDao;
import com.yskc.rs.dao.AppUserRoleDao;
import com.yskc.rs.entity.AppRole;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.AppUserRole;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.menu.MenuInfo;
import com.yskc.rs.models.role.*;
import com.yskc.rs.service.AppRoleService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author huronghua
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppRoleDao appRoleDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private AppMenuDao appMenuDao;

    /**
     * 获取所有的角色
     *
     * @param pageNo
     * @param pageSize
     * @param roleName
     * @return
     */
    @Override
    public PageModel<List<AppRoleModel>> getAppRoleList(int pageNo, int pageSize, String roleName) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, appRoleDao.getAppRoleList(page, roleName));
    }

    /**
     * 添加角色
     *
     * @param userId
     * @param model
     * @return
     */
    @Override
    public boolean addRole(Integer userId, RoleModel model) throws OwnerException {
        int count = appRoleDao.getRoleCountByName(model.getRoleName());
        if (count > 0) {
            throw new OwnerException(ErrorEnum.HAS_ROLE_NAME);
        }
        AppRole appRole = new AppRole();
        appRole.setCreateTime(new Date());
        appRole.setCreatorId(userId);
        appRole.setRemark(model.getRemark());
        appRole.setRoleName(model.getRoleName());
        return appRoleDao.insert(appRole) > 0;
    }

    /**
     * delRole
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delRole(Integer userId, RoleModel model) throws OwnerException {
        int count = appUserRoleDao.getCountByRoleId(model.getRoleId());
        if (count > 0) {
            throw new OwnerException(ErrorEnum.ROLE_TO_USER);
        }
        return appRoleDao.deleteById(model.getRoleId()) > 0;
    }

    /**
     * 更新角色信息
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean updateRole(Integer userId, RoleModel model) throws OwnerException {
        AppRole appRole = new AppRole();
        appRole.setRoleName(model.getRoleName());
        appRole.setRemark(model.getRemark());
        appRole.setId(model.getRoleId());
        return appRoleDao.updateById(appRole) > 0;
    }

    /**
     * 获取权限菜单
     *
     * @return
     */
    @Override
    public List<MenuActionModel> getRoleMenuMap() {
        final List<MenuActionModel>[] menuActionModels = new List[]{new ArrayList<>()};
        List<MenuInfo> menuInfos = appMenuDao.getAppMenus();
        List<MenuInfo> catalogList = menuInfos.stream().filter(a -> a.getParentId() == -1).collect(Collectors.toList());
        catalogList.forEach(a -> {
            List<MenuInfo> tempMenuList = menuInfos.stream().filter(b -> b.getParentId().equals(a.getId())).collect(Collectors.toList());
            Integer[] menuId = {0};
            tempMenuList.forEach(b -> {
                List<ActionModel> actionModelList = menuInfos.stream().filter(c -> c.getParentId().equals(b.getId())).map(m -> {
                    ActionModel actionModel = new ActionModel();
                    actionModel.setDefaultCheck(false);
                    actionModel.setMenuId(m.getId());
                    actionModel.setMenuName(m.getName());
                    return actionModel;
                }).collect(Collectors.toList());
                MenuActionModel menuActionModel = new MenuActionModel();
                if (!menuId[0].equals(b.getParentId())) {
                    menuId[0] = b.getParentId();
                    menuActionModel.setChangeSpan(true);
                } else {
                    menuActionModel.setChangeSpan(false);
                }
                menuActionModel.setMenuCount(tempMenuList.size());
                menuActionModel.setActionModelList(actionModelList);
                menuActionModel.setCatalogName(a.getName());
                menuActionModel.setMenuId(b.getId());
                menuActionModel.setMenuName(b.getName());
                menuActionModels[0].add(menuActionModel);

            });

        });
        return menuActionModels[0];
    }

    /**
     *
     * @param userInfo
     * @param roleName
     * @return
     */
    @Override
    public List<AppRoleModel> getRoles(UserInfo userInfo, String roleName) {
        return appRoleDao.getRoles(roleName, userInfo.getCompanyId());
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        return appUserRoleDao.getUserRoleIds(userId,null);
    }

    /**
     *
     * @param addRoleModel
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean setUserRole(AddUserRoleModel addRoleModel, UserInfo userInfo) throws OwnerException {
        List<AppUserRole> userRoles = new ArrayList<>();
        Date now = new Date();
        Integer userId = addRoleModel.getUserId();
        addRoleModel.getRoleIds().forEach(item -> {
            userRoles.add(getUserRole(userInfo, now, userId, item));
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            appUserRoleDao.delByUserId(userId);
            if (userRoles.size() > 0) {
                appUserRoleDao.addBatch(userRoles);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("配置用户角色失败");
        }
    }

    /**
     *
     * @param userInfo
     * @param now
     * @param userId
     * @param roleId
     * @return
     */
    private AppUserRole getUserRole(UserInfo userInfo, Date now, Integer userId, Integer roleId) {
        AppUserRole appUserRole = new AppUserRole();
        appUserRole.setCreateTime(now);
        appUserRole.setRoleId(roleId);
        appUserRole.setCreatorId(userInfo.getId());
        appUserRole.setUserId(userId);
        appUserRole.setCompanyId(userInfo.getCompanyId());
        return appUserRole;
    }
}
