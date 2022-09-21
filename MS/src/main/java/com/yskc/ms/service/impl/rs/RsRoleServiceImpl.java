package com.yskc.ms.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.dao.rs.RsRoleDao;
import com.yskc.ms.dao.rs.RsRoleMenuDao;
import com.yskc.ms.dao.rs.RsUserRoleDao;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.entity.rs.RsRole;
import com.yskc.ms.entity.rs.RsRoleMenu;
import com.yskc.ms.entity.rs.RsUserRole;
import com.yskc.ms.entity.rs.models.role.*;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.role.QueryRsRoleModel;
import com.yskc.ms.models.role.UserRoleModel;
import com.yskc.ms.service.rs.RsRoleService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-30 17:01
 * @Description: rs角色业务实现层
 */
@Service
public class RsRoleServiceImpl implements RsRoleService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsRoleDao rsRoledao;

    @Autowired
    private RsUserRoleDao rsUserRoleDao;

    @Autowired
    private RsRoleMenuDao rsRoleMenuDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public PageModel<List<RsRoleModel>> getList(QueryRsRoleModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("ar.companyId");
            asc.add("c.companyName");
            page.setAscs(asc);
            page.setDescs(new ArrayList<>());
            page.getDescs().add("ar.createTime");
        }
        List<RsRoleModel> rsRoles = rsRoledao.getList(page, query);
        if (CollectionUtils.isEmpty(rsRoles)) {
            return PageUtils.build(page, rsRoles);
        }
        List<Integer> userIds = new ArrayList<>();
        List<Integer> selectedRoleIds = new ArrayList<>();
        rsRoles.forEach(rs -> {
            userIds.add(rs.getCreatorId());
            if (!StringUtils.isEmpty(rs.getSelectedRoleId())) {
                selectedRoleIds.add(rs.getSelectedRoleId());
            }
        });
        List<User> userList = userDao.selectBatchIds(userIds);
        Map<Integer, String> userMap = new HashMap<>(userList.size());
        if (!CollectionUtils.isEmpty(userList)) {
            userMap = userList.stream().collect(Collectors.toMap(User::getId, User::getUserName, (a, b) -> a));
        }
        Map<Integer, Boolean> selectRoleMap = new HashMap<>(userList.size());
        if (!CollectionUtils.isEmpty(selectedRoleIds)) {
            Set<Integer> roleIds = new HashSet<>();
            roleIds.addAll(rsUserRoleDao.getUserRoleIds(selectedRoleIds, query.getCompanyId()));
            roleIds.forEach(roleId -> {
                selectRoleMap.put(roleId, true);
            });
        }
        for (RsRoleModel model : rsRoles) {
            if (userMap.containsKey(model.getCreatorId())) {
                model.setUserName(userMap.get(model.getCreatorId()));
            }
            if (!StringUtils.isEmpty(model.getSelectedRoleId())) {
                if (selectRoleMap.containsKey(model.getSelectedRoleId())) {
                    model.setIsvisible(true);
                } else {
                    model.setIsvisible(false);
                }
            } else {
                model.setIsvisible(false);
            }

        }
        return PageUtils.build(page, rsRoles);
    }

    @Override
    public boolean add(RsRoleAddModel rsRoleAddModel, UserInfo userInfo) throws OwnerException {
        if (rsRoledao.checkRepeatCompanyRole(rsRoleAddModel.getCompanyId(), rsRoleAddModel.getRoleName()) > 0) {
            throw new OwnerException(ErrorEnum.HAS_ROLE_NAME);
        }
        RsRole rsRole = new RsRole();
        Date now = new Date();
        BeanUtil.copyProperties(rsRoleAddModel, rsRole);
        rsRole.setCreatorId(userInfo.getId());
        rsRole.setCreateTime(now);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsRoledao.insert(rsRole);
            Integer companyId = rsRoleAddModel.getCompanyId();
            if (companyId > 0) {
                rsRoledao.addCompanyRole(companyId, rsRole.getId(), userInfo.getId(), now);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public List<MiniModel> getCompany(String companyName) {
        if (StringUtils.isEmpty(companyName)) {
            return new ArrayList<>();
        }
        //查询前五条
        return rsRoledao.getCompany(companyName);
    }

    @Override
    public boolean update(RsRoleAddModel rsRoleAddModel, UserInfo userInfo) throws OwnerException {
        RsRole rsRole = rsRoledao.selectById(rsRoleAddModel.getId());
        if (!rsRole.getRoleName().equals(rsRoleAddModel.getRoleName())) {
            if (rsRoledao.checkRepeatCompanyRole(rsRoleAddModel.getCompanyId(), rsRoleAddModel.getRoleName()) > 0) {
                throw new OwnerException(ErrorEnum.HAS_ROLE_NAME);
            }
        }
        rsRole.setmType(rsRoleAddModel.getmType());
        rsRole.setProductType(rsRoleAddModel.getProductType());
        rsRole.setRemark(rsRoleAddModel.getRemark());
        rsRole.setRoleName(rsRoleAddModel.getRoleName());
        return rsRoledao.updateAllColumnById(rsRole) > 0;
    }

    @Override
    public boolean del(Integer id) throws OwnerException {
        if (rsUserRoleDao.getByRoleId(id) > 0) {
            throw new OwnerException(ErrorEnum.ROLE_TO_USER);
        }
        return rsRoledao.deleteById(id) > 0;
    }

    @Override
    public List<RsActionMenuModel> getMenuByUserId(Integer userId) {
        return rsUserRoleDao.getUserMenu(userId);
    }

    @Override
    public boolean setRole(SetRsRoleModel rsRoleModel, UserInfo info) throws OwnerException {
        RsRole rsRole = rsRoledao.selectById(rsRoleModel.getRoleId());
        if (null == rsRole) {
            throw new OwnerException("获取角色失败");
        }
        Arrays.sort(rsRoleModel.getMenuArr());

        Date now = new Date();
        List<RsRoleMenu> rsRoleMenus = new ArrayList<>();
        for (Integer menuId : rsRoleModel.getMenuArr()) {
            rsRoleMenus.add(getRoleMenu(now, info, menuId, rsRole));
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsRoleMenuDao.delByRoleId(rsRole.getId());
            if (!CollectionUtils.isEmpty(rsRoleMenus)) {
                rsRoleMenuDao.addBatch(rsRoleMenus);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException(MessageFormat.format("配置角色[{0}]功能失败。", rsRole.getRoleName()));
        } finally {
            deleteRsRole(rsRole.getId());
        }
    }

    private void deleteRsRole(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return;
        }
        try {
            String rsRoleKey = MessageFormat.format(Constant.REDIS_KEY_RS_ROLE, roleId);
            if (redisUtils.hasKey(rsRoleKey)) {
                redisUtils.del(rsRoleKey);
            }
        } catch (Exception e) {
            logger.error("deleteRsRoles", e);
        }
    }


    @Override
    public List<Integer> getRoleMenuIds(Integer roleId) {
        return rsRoleMenuDao.getRoleMenuIds(roleId);

    }


    @Override
    public boolean setCompanyRole(CompanyRoleModel companyRoleModel, UserInfo userInfo) throws OwnerException {
        Integer companyId = companyRoleModel.getCompanyId();
        Integer[] roleIds = companyRoleModel.getRoleIds();
        List<Integer> pageRoleIds = companyRoleModel.getPageRoleIds();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsRoledao.delCompanyRoleByCompanyId(companyId, pageRoleIds);
            if (roleIds.length > 0) {
                Date now = new Date();
                rsRoledao.addBatchCompanyRole(companyId, roleIds, userInfo.getId(), now);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    private RsRoleMenu getRoleMenu(Date now, UserInfo info, Integer menuId, RsRole rsRole) {
        RsRoleMenu rsRoleMenu = new RsRoleMenu();
        rsRoleMenu.setCompanyId(rsRole.getCompanyId());
        rsRoleMenu.setCreatorId(info.getId());
        rsRoleMenu.setCreateTime(now);
        rsRoleMenu.setMenuId(menuId);
        rsRoleMenu.setRoleId(rsRole.getId());
        return rsRoleMenu;
    }

    /**
     * 获取已经分配给用户的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getRoleUserId(Integer userId, Integer companyId) {
        return rsRoledao.getRoleUserId(userId, companyId);
    }

    /**
     * 分配客户的用户角色
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean setUserRole(UserRoleModel model, UserInfo info) throws OwnerException {
        List<RsUserRole> userRoles = new ArrayList<>();
        Date now = new Date();
        Integer userId = model.getUserId();
        List<Integer> companyIds = model.getCompanyIds();
        if (!CollectionUtils.isEmpty(model.getRoleIds())) {
            model.getRoleIds().forEach(item -> {
                companyIds.forEach(cId->{
                    userRoles.add(getUserRole(info, now, userId, item, cId));
                });
            });
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsUserRoleDao.delByUserId(userId, companyIds, model.getPageRoleIds());
            if (userRoles.size() > 0) {
                rsUserRoleDao.addBatch(userRoles);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("配置用户角色失败");
        }
        redisUtils.set(MessageFormat.format(Constant.REDIS_KEY_RS_USER_UPDATE_TIME, userId), new Date(), Constant.ONE_DAY_TIME);
        return true;
    }

    private RsUserRole getUserRole(UserInfo userInfo, Date now, Integer userId, Integer roleId, Integer companyId) {
        RsUserRole appUserRole = new RsUserRole();
        appUserRole.setCreateTime(now);
        appUserRole.setRoleId(roleId);
        appUserRole.setCreatorId(userInfo.getId());
        appUserRole.setUserId(userId);
        appUserRole.setCompanyId(companyId);
        return appUserRole;
    }

    /**
     * 获取公司已经分配的角色
     *
     * @param companyId
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageModel<List<RsRoleModel>> getRoles(Integer companyId, String roleName, Integer pageNo, Integer pageSize) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, rsRoledao.getRoles(page, roleName, companyId));
    }


    @Override
    public PageModel<List<RsRoleModel>> queryCustomer(Integer roleId, String companyName, int pageNo, int pageSize) {
        Pagination page = new Pagination(pageNo, pageSize);
        List<RsRoleModel> rsRoleModels = rsRoledao.queryCustomer(page, roleId, companyName);
        return PageUtils.build(page, rsRoleModels);
    }


}
