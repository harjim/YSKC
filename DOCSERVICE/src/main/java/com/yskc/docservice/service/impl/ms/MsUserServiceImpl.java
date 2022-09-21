package com.yskc.docservice.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.RedisUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.dao.ms.MsAppUserRoleDao;
import com.yskc.docservice.entity.ms.Dept;
import com.yskc.docservice.entity.ms.MsAppUserRole;
import com.yskc.docservice.models.ms.MsUserInfo;
import com.yskc.docservice.models.ms.login.MenuPermModel;
import com.yskc.docservice.models.ms.login.MsUserSession;
import com.yskc.docservice.models.ms.role.UserRoleDept;
import com.yskc.docservice.service.ms.MsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 14:14
 * @Description: ms用户业务实现层
 */
@Service
public class MsUserServiceImpl implements MsUserService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MsAppUserRoleDao msAppUserRoleDao;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @Override
    public MsUserInfo getUserInfoByToken(String token) throws OwnerException {
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_MS_SESSION, token);
        if (redisUtils.hasKey(sessionKey)) {
            MsUserSession userSession = redisUtils.get(sessionKey, MsUserSession.class);
            String msUserRoleKey = MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, userSession.getId());
            List<UserRoleDept> userRoleDepts;
            if (!redisUtils.hasKey(msUserRoleKey)) {
                List<MsAppUserRole> userRoles = msAppUserRoleDao.getByUserIds(Arrays.asList(userSession.getId()));
                List<Integer> deptIds = new ArrayList<>();
                userRoleDepts = new ArrayList<>();
                // 取出当前用户所在的各个角色所服务的部门
                for (MsAppUserRole userRole :
                        userRoles) {
                    // 构建用户所在角色部门对象
                    UserRoleDept urd = new UserRoleDept();
                    urd.setUserId(userSession.getId());
                    urd.setRoleId(userRole.getRoleId());
                    // 默认取用户所在部门
                    if (userRole.getServiceDeptIds() == null) {
                        urd.setDeptIds(userSession.getDeptIds());
                        urd.setFullPaths(userSession.getDeptFullPaths());
                    } else {
                        String[] deptStrIds = userRole.getServiceDeptIds().split(",");
                        List<Integer> roleDeptIds = new ArrayList<>();
                        for (String strId :
                                deptStrIds) {
                            Integer dId = Integer.parseInt(strId);
                            roleDeptIds.add(dId);
                            deptIds.add(dId);
                        }
                        urd.setDeptIds(roleDeptIds);
                    }
                    userRoleDepts.add(urd);
                }
                if (deptIds.size() > 0) {
                    // 取出所有服务部门数据,并形成map
                    List<Dept> depts = msAppUserRoleDao.getByDeptIds(deptIds);
                    Map<Integer, Dept> deptMap = new HashMap<>();
                    for (Dept dept :
                            depts) {
                        deptMap.put(dept.getId(), dept);
                    }
                    // 用户所在角色部门全路径fullPath赋值
                    for (UserRoleDept urd :
                            userRoleDepts) {
                        if (urd.getFullPaths() == null) {
                            urd.setFullPaths(new ArrayList<>());
                            for (Integer dId :
                                    urd.getDeptIds()) {
                                if (deptMap.containsKey(dId)) {
                                    urd.getFullPaths().add(deptMap.get(dId).getFullPath());
                                }
                            }
                        }
                    }
                }
                redisUtils.set(msUserRoleKey, userRoleDepts, Constant.ONE_DAY_TIME);
            } else {
                userRoleDepts = redisUtils.getAsList(msUserRoleKey, UserRoleDept.class);
            }
            redisUtils.expire(sessionKey, Constant.ONE_DAY_TIME);
            userSession.setRoleDepts(userRoleDepts);
            return buildUserInfo(userSession);
        }
        return null;
    }

    public MsUserInfo buildUserInfo(MsUserSession userSession) {
        MsUserInfo userInfo = new MsUserInfo();
        BeanUtil.copyProperties(userSession, userInfo);
        Collection<MenuPermModel> permList;
        if (docServiceConfig.getMsAdminName().equals(userSession.getUserName())) {
            permList = msAppUserRoleDao.getAdminPerms();
        } else {
            Map<Integer, MenuPermModel> roleMenuMap = new HashMap<>();
            // List<MenuPermModel> permModels = new ArrayList<>();
            // 判断角色
            if (!CollectionUtils.isEmpty(userSession.getRoleDepts())) {
                String roleKey;
                // 遍历角色，存在redis直接取
                for (UserRoleDept roleDept : userSession.getRoleDepts()) {
                    roleKey = MessageFormat.format(Constant.REDIS_KEY_MS_ROLE, roleDept.getRoleId());
                    List<MenuPermModel> tempRoleMenu;
                    if (redisUtils.hasKey(roleKey)) {
                        tempRoleMenu = redisUtils.getAsList(roleKey, MenuPermModel.class);
                    } else {
                        tempRoleMenu = msAppUserRoleDao.getPermData(roleDept.getRoleId());
                        redisUtils.set(roleKey, tempRoleMenu, Constant.ONE_DAY_TIME);
                    }
                    // 获取各个功能所拥有的数据权限,多角色以最大数据权限的那个为准,前提:按功能级别排序
                    for (MenuPermModel menuPermModel :
                            tempRoleMenu) {
                        Integer mId = menuPermModel.getMenuId();
                        MenuPermModel pPerm = null;
                        Integer pId = menuPermModel.getParentId();
                        if (pId != null)
                            pPerm = roleMenuMap.get(pId);
                        // 当前功能没有数据权限,跟随父功能
                        if (menuPermModel.getDataType() == null && pPerm != null && pPerm.getDataType() != null) {
                            menuPermModel.setDataType(pPerm.getDataType());
                        }
                        MenuPermModel tmpPerm = roleMenuMap.get(mId);
                        if (tmpPerm == null) {
                            tmpPerm = menuPermModel;
                            roleMenuMap.put(mId, tmpPerm);
                        } else if (menuPermModel.getDataType() != null
                                && (tmpPerm.getDataType() == null
                                || tmpPerm.getDataType() < menuPermModel.getDataType())) {
                            // 以最大数据权限的那个角色为准
                            tmpPerm.setDataType(menuPermModel.getDataType());
                        }
                        // 部门权限
                        if (tmpPerm.getDeptFullPaths() == null) {
                            tmpPerm.setDeptFullPaths(new ArrayList<>());
                        }
                        for (String fullPath :
                                roleDept.getFullPaths()) {
                            if (!tmpPerm.getDeptFullPaths().contains(fullPath)) {
                                tmpPerm.getDeptFullPaths().add(fullPath);
                            }
                        }
                    }
                    // permModels.addAll(tempRoleMenu);
                }
            }
            permList = roleMenuMap.values();
        }
        Map<String, Boolean> permDataMap = new HashMap<>();
        permList.forEach(item -> permDataMap.put(item.getPerms(), true));
        userInfo.setPermDataMap(permDataMap);
        return userInfo;
    }
}
