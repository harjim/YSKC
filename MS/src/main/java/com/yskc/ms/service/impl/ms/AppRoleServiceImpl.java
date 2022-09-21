package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.menu.MenuInfo;
import com.yskc.ms.models.role.UserDept;
import com.yskc.ms.models.role.*;
import com.yskc.ms.service.ms.AppRoleService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author huronghua
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {
    @Autowired
    private AppRoleDao appRoleDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private AppMenuDao appMenuDao;
    @Autowired
    private AppRoleMenuDao appRoleMenuDao;
    @Autowired
    private AppRoleDataDao appRoleDataDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DeptDao deptDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取所有的角色
     *
     * @return
     */
    @Override
    public PageModel<List<AppRoleModel>> getAppRoleList(QueryRoleModel query, UserInfo userInfo, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("a.createTime");
            page.setDescs(desc);
        }
        List<AppRoleModel> appRoleModelList = appRoleDao.getAppRoleList(page, query, perm);
        return PageUtils.build(page, appRoleModelList);
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            appRoleDao.deleteById(model.getRoleId());
            List<Integer> roleIds = CollUtil.newArrayList(model.getRoleId());
            appRoleMenuDao.delRoleMenuByRoleIds(roleIds);
            appRoleDataDao.deleteByRoleIds(roleIds);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("删除角色失败");
        }
        String roleKey = MessageFormat.format(Constant.REDIS_KEY_ROLE, model.getRoleId());
        if (redisUtils.hasKey(roleKey)) {
            redisUtils.del(roleKey);
        }
        return true;
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
    public List<MenuInfo> getRoleMenuMap() {
        return appMenuDao.getAppMenus();
//        catalogList.forEach(a -> {
//            List<MenuInfo> tempMenuList = menuInfos.stream().filter(b -> b.getParentId().equals(a.getId())).collect(Collectors.toList());
//            Integer[] menuId = {0};
//            tempMenuList.forEach(b -> {
//                List<ActionModel> actionModelList = menuInfos.stream().filter(c -> c.getParentId().equals(b.getId())).map(m -> {
//                    ActionModel actionModel = new ActionModel();
//                    actionModel.setDefaultCheck(false);
//                    actionModel.setMenuId(m.getId());
//                    actionModel.setMenuName(m.getName());
//                    return actionModel;
//                }).collect(Collectors.toList());
//                MenuActionModel menuActionModel = new MenuActionModel();
//                if (!menuId[0].equals(b.getParentId())) {
//                    menuId[0] = b.getParentId();
//                    menuActionModel.setChangeSpan(true);
//                } else {
//                    menuActionModel.setChangeSpan(false);
//                }
//                menuActionModel.setType(b.getType());
//                menuActionModel.setParentId(b.getParentId());
//                menuActionModel.setMenuCount(tempMenuList.size());
//                menuActionModel.setActionModelList(actionModelList);
//                menuActionModel.setCatalogName(a.getName());
//                menuActionModel.setMenuId(b.getId());
//                menuActionModel.setMenuName(b.getName());
//                menuActionModels.add(menuActionModel);
//
//            });
//
//        });
//        return menuActionModels;
//
    }

    /**
     * 获取角色菜单权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<RoleDataModel> getRoleMenuIds(Integer roleId) {
        return appRoleMenuDao.getRoleMenuIds(roleId);
    }

    /**
     * 配置角色权限
     *
     * @param userId
     * @param model
     * @return
     */
    @Override
    public boolean setRole(Integer userId, SetRoleModel model) throws OwnerException {
        Date now = new Date();
        List<Integer> menuIds = model.getMenuIdList();
        List<RoleDataModel> dataPerms = model.getDataPerms();
        Integer currentRoleId = model.getRoleId();
        List<AppRoleMenu> appRoleMenuList = new ArrayList<>();
        List<AppRoleData> appRoleDataList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.addAll(model.getMenuIdList());
            menuIds = menuIds.stream().distinct().collect(Collectors.toList());
            for (Integer menuId : menuIds) {
                AppRoleMenu roleMenu = new AppRoleMenu();
                roleMenu.setCreateTime(now);
                roleMenu.setCreatorId(userId);
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(currentRoleId);
                appRoleMenuList.add(roleMenu);
            }
        }
        if (!CollectionUtils.isEmpty(dataPerms)) {
            for (RoleDataModel dataPerm : dataPerms) {
                appRoleDataList.add(AppRoleData.build(userId, currentRoleId, now, dataPerm.getMenuId(), dataPerm.getDataType()));
            }
        }

        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            appRoleMenuDao.delRoleMenuByRoleIds(CollUtil.newArrayList(model.getRoleId()));
            if (!CollectionUtils.isEmpty(appRoleMenuList)) {
                appRoleMenuDao.insertByBatch(appRoleMenuList);
            }
            appRoleDataDao.deleteByRoleIds(CollUtil.newArrayList(currentRoleId));
            if (!CollectionUtils.isEmpty(appRoleDataList)) {
                appRoleDataDao.insertBatch(appRoleDataList);
            }
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        String roleKey = MessageFormat.format(Constant.REDIS_KEY_ROLE, model.getRoleId());
        if (redisUtils.hasKey(roleKey)) {
            redisUtils.del(roleKey);
        }
        return true;
    }

    /**
     * 设置用户权限
     *
     * @param userId
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean setUserRole(Integer userId, UserRoleModel model) throws OwnerException {
        List<AppUserRole> appUserRoleList = getUserRoleList(userId, model, model.getUserId());
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            appUserRoleDao.deleteUserRole(CollUtil.newArrayList(model.getUserId()));
            if (appUserRoleList.size() > 0) {
                appUserRoleDao.addBatch(appUserRoleList);
            }
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        String msUserRoleKey = MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, model.getUserId());
        if (redisUtils.hasKey(msUserRoleKey)) {
            redisUtils.del(msUserRoleKey);
        }
        return true;
    }

    @Override
    public boolean setUserRoles(Integer userId, UserRoleModel model) throws OwnerException {
        List<AppUserRole> allList = new ArrayList<>();
        model.getUserIds().forEach(item -> {
            allList.addAll(getUserRoleList(userId, model, item));
        });
        List<AppUserRole> existList = appUserRoleDao.getByUserIds(model.getUserIds());
        String defaultValue = "";
        Map<String, String> existUserRoleIdMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existList)) {
            existList.forEach(item -> existUserRoleIdMap.put(item.getUserId() + "" + item.getRoleId(), defaultValue));
        }
        List<AppUserRole> insertList = new ArrayList<>();
        allList.forEach(item -> {
            if (!existUserRoleIdMap.containsKey(item.getUserId() + "" + item.getRoleId())) {
                insertList.add(item);
            }
        });
        try {
            if (insertList.size() > 0) {
                appUserRoleDao.addBatch(insertList);
            }
        } catch (Exception ex) {
            throw new OwnerException("批量设置角色失败");
        }
        List<String> keys = new ArrayList<>();
        for (Integer msUserId : model.getUserIds()) {
            keys.add(MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, msUserId));
        }
        redisUtils.del(keys);
        return true;
    }

    private List<AppUserRole> getUserRoleList(Integer userId, UserRoleModel model, Integer roleUserId) {
        Date now = new Date();
        List<AppUserRole> appUserRoleList = new ArrayList<>();
        model.getRoleIds().forEach(a -> {
            AppUserRole appUserRole = new AppUserRole();
            appUserRole.setCreatorId(userId);
            appUserRole.setRoleId(a);
            appUserRole.setUserId(roleUserId);
            appUserRole.setCreateTime(now);
            appUserRoleList.add(appUserRole);
        });

        return appUserRoleList;
    }

    /**
     * 获取用户角色关联信息
     *
     * @param userId
     * @return
     * @throws OwnerException
     */
    @Override
    public UserRoleModel getUserRoleIds(Integer userId) throws OwnerException {
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUserId(userId);
        userRoleModel.setRoleIds(appUserRoleDao.getUserRoleIds(userId));
        return userRoleModel;
    }

    @Override
    public boolean delBatch(List<RoleModel> models) throws OwnerException {
        List<Integer> ids = models.stream().map(RoleModel::getId).collect(Collectors.toList());
        List<Integer> countIds = appUserRoleDao.getCountByListRoleId(ids);
        Collection<Integer> result = CollUtil.disjunction(ids, countIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选角色已全部关联用户，不能删除!");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            appRoleDao.deleteBatchIds(result);
            List<Integer> roleIds = new ArrayList<>();
            roleIds.addAll(result);
            appRoleMenuDao.delRoleMenuByRoleIds(roleIds);
            appRoleDataDao.deleteByRoleIds(roleIds);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("删除角色失败");
        }
        return true;
    }


    @Override
    public PageModel<List<DeptUserInfo>> getUserByRoleId(QueryRoleManageModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("y.id");
            page.setAscs(ascs);
        }
        PageModel<List<DeptUserInfo>> result = PageUtils.build(page, appRoleDao.getUserByRoleId(page, query));
        if (result.getData().size() > 0) {
            List<Integer> deptIds = new ArrayList<>();
            for (DeptUserInfo userInfo :
                    result.getData()) {
                if (userInfo.getServiceDeptIds() != null && userInfo.getServiceDeptIds() != "") {
                    String[] deptIdStrs = userInfo.getServiceDeptIds().split(",");
                    for (String deptIdStr :
                            deptIdStrs) {
                        Integer deptId = Integer.parseInt(deptIdStr);
                        if (!deptIds.contains(deptId)) {
                            deptIds.add(deptId);
                        }
                    }
                } else {
                    userInfo.setServiceDeptNames(userInfo.getDeptName());
                }
            }
            if (deptIds.size() > 0) {
                // 取出所有服务部门数据,并形成map
                List<Dept> depts = deptDao.getByDeptIds(deptIds);
                Map<Integer, Dept> deptMap = new HashMap<>();
                for (Dept dept :
                        depts) {
                    deptMap.put(dept.getId(), dept);
                }
                for (DeptUserInfo userInfo :
                        result.getData()) {
                    if (userInfo.getServiceDeptIds() != null && userInfo.getServiceDeptIds() != "") {
                        String[] deptIdStrs = userInfo.getServiceDeptIds().split(",");
                        List<String> deptNames = new ArrayList<>();
                        for (String deptIdStr :
                                deptIdStrs) {
                            Integer deptId = Integer.parseInt(deptIdStr);
                            if (deptMap.get(deptId) != null) {
                                if (userInfo.getServiceDeptNames() == null || userInfo.getServiceDeptNames() == "") {
                                    userInfo.setServiceDeptNames(deptMap.get(deptId).getDeptName());
                                } else {
                                    userInfo.setServiceDeptNames(userInfo.getServiceDeptNames() + "," + deptMap.get(deptId).getDeptName());
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public PageModel<List<DeptUserInfo>> selectUserData(int pageNo, int pageSize, Integer roleId, String realName, String fullPath) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, appRoleDao.selectUserData(page, roleId, realName, fullPath));

    }

    @Override
    public boolean deleteBatchUser(List<DeptUserInfo> deptUserInfos) {
        List<Integer> userIds = deptUserInfos.stream().map(DeptUserInfo::getId).collect(Collectors.toList());
        Integer roleId = deptUserInfos.get(0).getRoleId();
        List<String> keys = new ArrayList<>();
        for (Integer msUserId : userIds) {
            keys.add(MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, msUserId));
        }
        redisUtils.del(keys);
        return appRoleDao.deleteBatchUser(userIds, roleId);
    }

    @Override
    public boolean updateServiceDept(UserDept userDept) {
        // 更新服务部门的时候,清除用户角色缓存
        List<String> keys = new ArrayList<>();
        for (Integer msUserId : userDept.getUserIds()) {
            keys.add(MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, msUserId));
        }
        redisUtils.del(keys);
        return appRoleDao.updateServiceDept(userDept);
    }
}
