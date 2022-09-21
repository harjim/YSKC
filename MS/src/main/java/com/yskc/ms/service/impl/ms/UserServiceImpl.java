package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.common.utils.TokenGenerator;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.ManagerUserDao;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.enums.UserStatusEnum;
import com.yskc.ms.models.ModifyPasswordModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptModel;
import com.yskc.ms.models.dingding.Department;
import com.yskc.ms.models.dingding.DingUser;
import com.yskc.ms.models.dingding.DingUserModel;
import com.yskc.ms.models.login.LoginInfo;
import com.yskc.ms.models.login.UserSession;
import com.yskc.ms.models.mobile.CodeLoginModel;
import com.yskc.ms.models.role.MenuPermModel;
import com.yskc.ms.models.role.UserRoleDept;
import com.yskc.ms.models.user.*;
import com.yskc.ms.service.ms.DeptService;
import com.yskc.ms.service.ms.UserService;
import com.yskc.ms.utils.DingUtils;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author huronghua
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysSessionDao sysSessionDao;
    @Autowired
    private DingUserDao dingUserDao;
    @Autowired
    private DingUtils dingUtils;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserDeptDao userDeptDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ManagerUserDao managerUserDao;
    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private AppRoleMenuDao appRoleMenuDao;

    @Autowired
    private DeptDao deptDao;

    /**
     * 缓存UserSession
     *
     * @param user
     */
    public String getToken(User user, String logDesc, String requestIp, PlatformEnum platform) {
        List<DeptModel> deptModels = deptDao.getDeptByUserId(user.getId());
        List<Integer> deptIds = new ArrayList<>();
        List<String> deptFullPaths = new ArrayList<>();
        for (DeptModel deptModel :
                deptModels) {
            deptIds.add(deptModel.getId());
            deptFullPaths.add(deptModel.getFullPath());
        }
        String token = TokenGenerator.generateValue();
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
        UserSession userSession = new UserSession();
        BeanUtil.copyProperties(user, userSession);
        // 添加当前用户所属部门信息至session
        userSession.setDeptIds(deptIds);
        userSession.setDeptFullPaths(deptFullPaths);
        userSession.setToken(token);
        userSession.setCreateTime(new Date());
        userSession.setPlatform(platform.getPlatform());
        // userSession.setRoleIds(appUserRoleDao.getUserRoleIds(user.getId()));
        redisUtils.set(sessionKey, userSession, Constant.ONE_DAY_TIME);
        // pc端登录刷新token
        if (platform == PlatformEnum.PC) {
            String lastToken;
            if (!StringUtils.isEmpty(user.getToken()) && redisUtils.hasKey(lastToken = MessageFormat.format(Constant.REDIS_KEY_SESSION, user.getToken()))) {
                redisUtils.del(lastToken);
            }
            userDao.updateToken(user.getId(), token);
        }
        sysLogDao.insert(SysLog.build(user.getId(), user.getUserName(), user.getRealName(), logDesc,
                "/api/user/login", "用户名：" + user.getUserName(), requestIp));
        return token;
    }

    /**
     * 用户登录
     *
     * @param loginInfo
     * @return
     * @throws OwnerException
     */
    @Override
    public String login(LoginInfo loginInfo, String requestIp) throws OwnerException {
        User user = verifyUsernamePassword(loginInfo);
        return getToken(user, "用户登录", requestIp, PlatformEnum.PC);

    }

    @Override
    public String ddLogin(LoginInfo loginInfo, String requestIp) throws OwnerException {
        DingUserModel dingUserModel = dingUtils.getUserUnionIdByCode(loginInfo.getLoginTmpCode(), requestIp);
        if (dingUserModel == null) {
            throw new OwnerException("无效用户，请联系管理员");
        }
        return unionidLogin(dingUserModel.getUnionid(), "钉钉扫码登录", requestIp, PlatformEnum.PC);
    }

    private String unionidLogin(String unionid, String desc, String requestIp, PlatformEnum platform) throws OwnerException {
        User user = userDao.getByUnionId(unionid);
        if (null == user) {
            throw new OwnerException("无效用户，请联系管理员");
        } else if (user.getStatus() == 1) {
            throw new OwnerException("帐户已禁用，请联系管理员");
        }
        return getToken(user, desc, requestIp, platform);
    }


    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public UserInfo getUserInfo(String token) {
        SysSession sysSession = sysSessionDao.getSessionByToken(token);
        return userDao.getUser(sysSession.getUserId());
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public UserInfo getUserInfoByToken(String token) {
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
        if (redisUtils.hasKey(sessionKey)) {
            UserSession userSession = redisUtils.get(sessionKey, UserSession.class);
            String msUserRoleKey = MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, userSession.getId());
            List<UserRoleDept> userRoleDepts;
            if (!redisUtils.hasKey(msUserRoleKey)) {
                List<AppUserRole> userRoles = appUserRoleDao.getByUserIds(Arrays.asList(userSession.getId()));
                List<Integer> deptIds = new ArrayList<>();
                userRoleDepts = new ArrayList<>();
                // 取出当前用户所在的各个角色所服务的部门
                for (AppUserRole userRole : userRoles) {
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
                    List<Dept> depts = deptDao.getByDeptIds(deptIds);
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

    public UserInfo buildUserInfo(UserSession userSession) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userSession, userInfo);
        Integer platform = userSession.getPlatform();
        Collection<MenuPermModel> permList;
        if (msConfig.getLogin().getAdminName().equals(userSession.getUserName())) {
            permList = userDao.getAdminPerms();
        } else {
            Map<Integer, MenuPermModel> roleMenuMap = new HashMap<>();
            // List<MenuPermModel> permModels = new ArrayList<>();
            // 判断角色
            if (!CollectionUtils.isEmpty(userSession.getRoleDepts())) {
                String roleKey;
                // 遍历角色，存在redis直接取
                for (UserRoleDept roleDept : userSession.getRoleDepts()) {
                    roleKey = MessageFormat.format(Constant.REDIS_KEY_ROLE, roleDept.getRoleId());
                    List<MenuPermModel> tempRoleMenu;
                    if (redisUtils.hasKey(roleKey)) {
                        tempRoleMenu = redisUtils.getAsList(roleKey, MenuPermModel.class);
                    } else {
                        tempRoleMenu = appRoleMenuDao.getPermData(roleDept.getRoleId());
                        redisUtils.set(roleKey, tempRoleMenu, Constant.ONE_DAY_TIME);
                    }
                    // 获取各个功能所拥有的数据权限,多角色以最大数据权限的那个为准,前提:按功能级别排序
                    for (MenuPermModel menuPermModel :
                            tempRoleMenu) {
                        // 跳过菜单类型和登录类型不一致的权限
                        if (!platform.equals(menuPermModel.getPlatform())) {
                            continue;
                        }
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
        Map<String, MenuPermModel> permDataMap = new HashMap<>();
        permList.forEach(item -> {
            permDataMap.put(item.getPerms(), item);
        });
        userInfo.setPermDataMap(permDataMap);
        return userInfo;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param modifyPasswordModel
     * @return
     */
    @Override
    public Boolean updatePassword(String token, int userId, String userName, ModifyPasswordModel modifyPasswordModel) {
        String oldPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, modifyPasswordModel.getOldPassword()));
        String newPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, modifyPasswordModel.getNewPassword()));
        boolean updated = userDao.updatePassword(userId, oldPassword, newPassword) > 0;
        if (updated) {
            String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
            UserSession userSession = redisUtils.get(sessionKey, UserSession.class);
            if (null != userSession) {
                userSession.setReSetPassword(true);
                redisUtils.set(sessionKey, userSession, Constant.ONE_DAY_TIME);
            }
        }
        return updated;
    }

    @Override
    public Boolean addUser(Integer currentUserId, UserModel model) throws OwnerException {
        if (StringUtils.isEmpty(model.getPassword())) {
            throw new OwnerException(ErrorEnum.USER_PASSOWRD_NULL);
        }
        User user = userDao.getUserInfo(model.getUserName());
        if (user != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }
        user = new User();
        user.setCreateTime(new Date());
        user.setCreatorId(currentUserId);
        user.setDepId("0");
        user.setGender(model.getGender());
        user.setRealName(model.getRealName());
        user.setUserName(model.getUserName());
        user.setRemark(model.getRemark());
        user.setTel(model.getTel());
        user.setPostion(model.getPostion());
        user.setReSetPassword(false);
        String pwd = Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword()));
        user.setPassword(pwd);
        user.setMtypes(model.getMtypes());
        String[] deptIdArr = model.getDeptIdArr();
        List<UserDept> insertUserDepts = new ArrayList<>();

        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            userDao.insert(user);

            for (String deptId : deptIdArr) {
                UserDept userDept = new UserDept();
                userDept.setUserId(user.getId());
                userDept.setDepId(Integer.parseInt(deptId));
                userDept.setAdmin(false);
                userDept.setUnionid("");
                userDept.setDingUserId("");
                insertUserDepts.add(userDept);
            }
            if (insertUserDepts.size() > 0) {
                userDeptDao.insertBatch(insertUserDepts);
            }

            TransactionUtils.commit(DataSourceEnum.MS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public Boolean updateUser(Integer currentUserId, UserModel model) throws OwnerException {
        Integer userId = model.getId();
        User user = userDao.selectById(userId);
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }

        user.setGender(model.getGender());
        if (model.getDeptIdArr().length > 0) {
            user.setDepId(model.getDeptIdArr()[0]);
        }
        user.setRealName(model.getRealName());
        user.setUserName(model.getUserName());
        user.setRemark(model.getRemark());
        user.setPostion(model.getPostion());
        user.setTel(model.getTel());
        user.setMtypes(model.getMtypes());
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            userDao.updateAllColumnById(user);
            List<String> oList = model.getOldDeptId() == null ? new ArrayList<>() : Arrays.asList(model.getOldDeptId());
            List<String> dList = Arrays.asList(model.getDeptIdArr());

            List<Integer> delIds = new ArrayList<>();
            for (String id : oList) {
                if (!dList.contains(id)) {
                    delIds.add(Integer.parseInt(id));
                }
            }
            if (delIds.size() > 0) {
                userDeptDao.deleteByUserIdAndDeptIds(userId, delIds);
            }

            List<Integer> addDeptIds = new ArrayList<>();
            for (String id : dList) {
                if (!oList.contains(id)) {
                    addDeptIds.add(Integer.parseInt(id));
                }
            }
            if (addDeptIds.size() > 0) {
                DingUser dingUser = dingUserDao.queryDUserByUserId(userId);
                String dingUserId = "";
                String unionid = "";
                if (dingUser != null) {
                    dingUserId = dingUser.getDingUserId();
                    unionid = dingUser.getUnionid();
                }
                List<UserDept> addUserDepts = new ArrayList<>();
                for (Integer deptId : addDeptIds) {
                    UserDept userDept = new UserDept();
                    userDept.setUserId(userId);
                    userDept.setDepId(deptId);
                    userDept.setDingUserId(dingUserId);
                    userDept.setUnionid(unionid);
                    userDept.setAdmin(false);
                    addUserDepts.add(userDept);
                }
                if (addUserDepts.size() > 0) {
                    userDeptDao.insertBatch(addUserDepts);
                }
            }
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
        //清除掉用户部门缓存
        redisUtils.del(MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, userId));
        return true;
    }

    /**
     * 重置密码
     *
     * @param model
     * @return
     */
    @Override
    public Boolean resetPassword(ResetPasswordModel model) {
        String pwd = Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword()));
        return userDao.resetPassword(model.getId(), pwd) > 0;
    }

    /**
     * 验证用户名，密码
     *
     * @param loginInfo
     * @return
     * @throws OwnerException
     */
    private User verifyUsernamePassword(LoginInfo loginInfo) throws OwnerException {
        User userInfo = userDao.getUserInfo(loginInfo.getUserName());
        if (userInfo == null) {
            throw new OwnerException("账户或密码错误，请重试或者联系管理员");
        }
        String pwd = Md5Util.encrypt(MessageFormat.format("{0}{1}", loginInfo.getUserName(), loginInfo.getPassword()));
        if (!pwd.equalsIgnoreCase(userInfo.getPassword())) {
            throw new OwnerException("账户或密码错误，请重试或者联系管理员");
        }
        return userInfo;
    }

    @Override
    public Boolean uploadDing(UserInfo info) throws OwnerException {
        Date now = new Date();
        List<Integer> deptIds = deptService.queryDeptIds();
        Set<String> usedIdSet = new HashSet<>();
        List<User> userList = userDao.queryUserAll();
        List<DingUserEntity> dUserList = dingUserDao.queryDUserAll();
        Map<String, DingUserEntity> dUserDidMap = dUserList.stream().collect(Collectors.toMap(DingUserEntity::getDingUserId, b -> b));
        Map<Integer, User> userEntityMap = userList.stream().collect(Collectors.toMap(User::getId, b -> b));
        for (int i = 0; i < deptIds.size(); i++) {
            List<String> userids = dingUtils.getDeptMember(deptIds.get(i));
            usedIdSet.addAll(userids);
        }

        //TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            for (String userId : usedIdSet) {
                int uid = 0;
                DingUser userInfo = dingUtils.getUserInfo(userId);
                DingUserEntity dUserEntity = dUserDidMap.get(userInfo.getUserid());
                if (dUserEntity == null) {
                    User entity = new User();
                    entity.setUserName(userInfo.getMobile());
                    String newPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", userInfo.getMobile(), "123456"));
                    entity.setPassword(newPassword);
                    entity.setRealName(userInfo.getName());
                    entity.setTel(userInfo.getMobile());
                    entity.setDepId(userInfo.getDepartment().get(0) + "");
                    entity.setPostion(userInfo.getPosition());
                    entity.setStatus(0);
                    entity.setAvatar("");
                    entity.setCreatorId(info.getId());
                    entity.setCreateTime(now);
                    entity.setRemark("钉钉同步");
                    userDao.insert(entity);
                    uid = entity.getId();
                    DingUserEntity dUser = new DingUserEntity();
                    dUser.setUserId(entity.getId());
                    dUser.setDingUserId(userInfo.getUserid());
                    dUser.setUnionid(userInfo.getUnionid());
                    dUser.setOpenid("");
                    dingUserDao.insert(dUser);
                } else {
                    User user = userEntityMap.get(dUserEntity.getUserId());
                    user.setTel(userInfo.getMobile());
                    user.setDepId(userInfo.getDepartment().get(0) + "");
                    user.setPostion(userInfo.getPosition());
                    userDao.updateById(user);
                    uid = user.getId();
                    userDeptDao.deleteByUserId(uid);
                }

                for (int i = 0; i < userInfo.getDepartment().size(); i++) {
                    UserDept userDept = new UserDept();
                    userDept.setUserId(uid);
                    userDept.setDepId(userInfo.getDepartment().get(i).intValue());
                    userDept.setDingUserId(userInfo.getUserid());
                    userDept.setUnionid(userInfo.getUnionid());
                    Department deManager = dingUtils.getDepartment(userInfo.getDepartment().get(i).intValue());
                    if (!StringUtils.isEmpty(deManager.getDeptManagerUseridList())
                            && deManager.getDeptManagerUseridList().indexOf(userInfo.getUserid()) != -1) {
                        userDept.setAdmin(true);
                    } else {
                        userDept.setAdmin(false);
                    }

                    userDeptDao.insert(userDept);
                }
            }
            return true;
        } catch (Exception ex) {
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public Boolean logout(String token) {
        if (!StringUtils.isEmpty(token)) {
            redisUtils.del(MessageFormat.format(Constant.REDIS_KEY_SESSION, token));
        }
        return true;
    }

    @Override
    public List<MiniUserModel> userForSelect(String realName, String fullPath, Boolean hasDept, Boolean noLeave) {
        if (StringUtils.isEmpty(realName)) {
            return new ArrayList<>();
        }
        List<MiniUserModel> userModels = userDao.userForSelect(realName, fullPath, noLeave);
        if (CollectionUtils.isEmpty(userModels)) {
            return new ArrayList<>();
        }
        if (hasDept != null && hasDept) {
            List<Integer> userIds = new ArrayList<>();
            Map<Integer, MiniUserModel> miniUserMap = new HashMap<>();
            for (MiniUserModel miniUser : userModels) {
                userIds.add(miniUser.getId());
                miniUserMap.put(miniUser.getId(), miniUser);
            }
            List<UserDeptModel> userDeptModels = userDeptDao.getByUserIds(userIds, fullPath);
            Map<Integer, UserDeptModel> map = userDeptModels.stream().collect(Collectors.toMap(a -> a.getUserId(), a -> a, (o, n) -> o));
            for (MiniUserModel model : userModels) {
                if (map.containsKey(model.getId())) {
                    Integer deptId = map.get(model.getId()).getDeptId();
                    if (deptId != null) {
                        model.setDeptId(deptId);
                        model.setDeptName(map.get(model.getId()).getDeptName());
                    }
                }
            }
        }
        return userModels;
    }

    @Override
    public List<MiniUserModel> userForDept(String realName, String fullPath, Boolean hasDept, Boolean noLeave) {
        List<MiniUserModel> userModels = userDao.userForDept(realName, fullPath, noLeave);
        if (CollectionUtils.isEmpty(userModels)) {
            return new ArrayList<>();
        }
        if (hasDept != null && hasDept) {
            List<Integer> userIds = new ArrayList<>();
            Map<Integer, MiniUserModel> miniUserMap = new HashMap<>();
            for (MiniUserModel miniUser : userModels) {
                userIds.add(miniUser.getId());
                miniUserMap.put(miniUser.getId(), miniUser);
            }
            List<UserDeptModel> userDeptModels = userDeptDao.getByUserIds(userIds, fullPath);
            Map<Integer, Integer> userDeptMap = userDeptModels.stream().collect(Collectors.toMap(a -> a.getUserId(), b -> b.getDeptId(), (c, d) -> c));
            for (MiniUserModel model : userModels) {
                Integer deptId = userDeptMap.get(model.getId());
                if (deptId != null) {
                    model.setDeptId(deptId);
                }
            }
        }
        return userModels;
    }

    @Override
    public List<MiniUserModel> getDeptUser(Integer deptId) {
        return userDeptDao.getUserByDeptId(deptId);
    }

    @Override
    public PageModel<List<OwnerUserModel>> getUserList(QueryUserModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, userDao.getUserList(page, query));
    }

    @Override
    public Boolean setDept(UserInfo userInfo, UserDeptModel model) {
        List<UserDeptModel> userList = model.getUserList();
        String[] deptIdArr = model.getDeptIdArr();
        List<UserDept> userDeptList = new ArrayList<>();
        for (UserDeptModel user : userList) {
            List<String> oldDeptIdList = new ArrayList<>();
            if (user.getDeptIdArr() != null) {
                oldDeptIdList = Arrays.asList(user.getDeptIdArr());
            }
            for (String deptId : deptIdArr) {
                if (!oldDeptIdList.contains(deptId)) {
                    UserDept userDept = new UserDept();
                    userDept.setUserId(user.getId());
                    userDept.setDepId(Integer.parseInt(deptId));
                    userDept.setAdmin(false);
                    userDept.setUnionid(user.getUnionid() != null ? user.getUnionid() : "");
                    userDept.setDingUserId(user.getDingUserId() != null ? user.getDingUserId() : "");
                    userDeptList.add(userDept);
                }
            }
        }
        if (userDeptList.size() > 0) {
            userDeptDao.insertBatch(userDeptList);
        }
        return true;
    }

    @Override
    public Boolean editStatus(Integer id, Integer status) throws OwnerException {
        User user = userDao.selectById(id);
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }
        user.setStatus(status == 0 ? UserStatusEnum.DISABLED.getType() : UserStatusEnum.ENABLED.getType());

        return userDao.updateById(user) > 0;
    }

    @Override
    public String loginByCode(CodeLoginModel codeLogin, String desc, String requestIp, PlatformEnum platform) throws OwnerException {
        DingUser dingUser = dingUtils.codeLogin(codeLogin);
        return unionidLogin(dingUser.getUnionid(), desc, requestIp, platform);
    }

    @Override
    public PageModel<List<User>> queryUserData(int pageNo, int pageSize, String userName, String realName, String tel, Integer companyId) throws OwnerException {
        Pagination page = new Pagination(pageNo, pageSize);
        List<Integer> existIds = managerUserDao.getManagerUserId(companyId);
        if (CollectionUtils.isEmpty(existIds)) {
            existIds = null;
        }
        return PageUtils.build(page, userDao.queryUserData(page, userName, realName, tel, existIds));
    }
}
