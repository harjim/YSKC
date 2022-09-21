package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.MsUserInfo;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.ProductMtype;
import com.yskc.common.model.RsMenuPermModel;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.common.utils.TokenGenerator;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.SysLog;
import com.yskc.rs.entity.SysSession;
import com.yskc.rs.entity.User;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.enums.CompanyGroupEnum;
import com.yskc.rs.enums.HighTechProgressNodeEnum;
import com.yskc.rs.enums.UserStatusEnum;
import com.yskc.rs.models.PwdMode;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyMember;
import com.yskc.rs.models.login.LoginInfo;
import com.yskc.rs.models.login.UserSession;
import com.yskc.rs.models.user.ChildUserModel;
import com.yskc.rs.models.user.QueryUserListModel;
import com.yskc.rs.models.user.RegisterModel;
import com.yskc.rs.service.UserService;
import com.yskc.rs.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * 用户服务实现
 *
 * @author huronghua
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysSessionDao sysSessionDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private AppRoleMenuDao appRoleMenuDao;


    @Override
    public Map login(LoginInfo loginInfo, String requestIp) throws OwnerException {
        String pwd = Md5Util.encrypt(MessageFormat.format("{0}{1}", loginInfo.getUserName(), loginInfo.getPassword()));
        User user = userDao.getUserInfo(loginInfo.getUserName());
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }
        if (user.getStatus() == UserStatusEnum.DISABLED.getType()) {
            throw new OwnerException(ErrorEnum.USER_DISABLED);
        }
        if (!pwd.equalsIgnoreCase(user.getPassword())) {
            throw new OwnerException(ErrorEnum.PASSWORD_ERROR);
        }
        Integer companyId = user.getCompanyId();
        CompanyMember companyMember = getCompanyMember(companyId, false);
        // List<Integer> roleIds = appUserRoleDao.getUserRoleIds(user.getId());
        String token = getToken(companyMember, user.getId(), user.getRealName(), user.getUserName(), user.getStatus(), 0, "用户登录", requestIp,user.getRoleType());
        redisUtils.set(MessageFormat.format(Constant.REDIS_USER_LOGIN_COMPANY_KEY, companyId, token), companyId, Constant.RS_USER_SESSION_TIMEOUT);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("companyId", user.getCompanyId());
        return resultMap;
    }

    @Override
    public Map jumpLogin(LoginInfo loginInfo, String requestIp) throws OwnerException {
        String tempKey = "tempToken:" + loginInfo.getLoginTmpCode();
        MsUserInfo msUserInfo = redisUtils.get(tempKey, MsUserInfo.class);
        if (null == msUserInfo) {
            throw new OwnerException("会话失效，请重试。");
        }
        redisUtils.del(tempKey);
        // 设置当前MS端用户的客户成员类型
        String newKey = MessageFormat.format("MsMType:{0}_{1}", msUserInfo.getUserId(), msUserInfo.getCurrentCompanyId().toString());
        //设置用户成员类型缓存时效为1天
        redisUtils.set(newKey, msUserInfo.getProductMtypes(), 86400L);
        CompanyMember companyMember = getCompanyMember(msUserInfo.getCurrentCompanyId(), false);
        // List<Integer> roleIds = managerRoleDao.getRoles(msUserInfo.getCurrentCompanyId(), msUserInfo.getMsUserIds());
        String token = getToken(companyMember, msUserInfo.getUserId(), msUserInfo.getRealName(), msUserInfo.getUserName(), 0, 1, "管理员登录", requestIp, HighTechProgressNodeEnum.DEFAULT.getRoleType());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("companyId", msUserInfo.getCurrentCompanyId());
        return resultMap;
    }

    /**
     * 获取token
     *
     * @param companyMember
     * @param userId
     * @param realName
     * @param userName
     * @param status
     * @param userSource
     * @param logDesc
     * @throws OwnerException
     */
    private String getToken(CompanyMember companyMember, Integer userId, String realName, String userName, Integer status, Integer userSource,
                            String logDesc, String requestIp,Integer roleType) {
        String token = TokenGenerator.generateValue();
        UserSession userSession = new UserSession();
        BeanUtil.copyProperties(companyMember, userSession);
        userSession.setGroupFullPath(companyMember.getFullPath());
        userSession.setId(userId);
        userSession.setRealName(realName);
        userSession.setUserName(userName);
        userSession.setStatus(status);
        userSession.setUserSource(userSource);
        userSession.setRoleType(roleType);
        userSession.setToken(token);
        userSession.setCreateTime(new Date());
        if (1 != userSource && CompanyGroupEnum.isGroup(companyMember.getCompanyType())) {
            userSession.setCompanyIds(appUserRoleDao.getCompanyIds(userId));
        }
        sysLogDao.insert(SysLog.build(userSession.getId(), userSession.getUserSource(), userSession.getUserName(), userSession.getRealName(), logDesc, "/api/user/login", "用户名: " + userSession.getUserName(), requestIp, userSession.getCompanyId(), userSession.getCompanyName()));
        long sessionTime = userSource == 1 ? Constant.MS_USER_SESSION_TIMEOUT : Constant.RS_USER_SESSION_TIMEOUT;
        redisUtils.set(MessageFormat.format(Constant.REDIS_KEY_SESSION, token), userSession, sessionTime);
        return token;
    }

    /**
     * 获取公司基础信息
     *
     * @param companyId
     * @return
     * @throws OwnerException
     */
    private CompanyMember getCompanyMember(Integer companyId, boolean fromCache) throws OwnerException {
        // 缓存公司基础数据
        String memberKey = MessageFormat.format("CMember:{0}", companyId);
        CompanyMember companyMember = null;
        if (fromCache) {
            companyMember = redisUtils.get(memberKey, CompanyMember.class);
        }
        if (companyMember == null) {
            companyMember = companyDao.getCompanyMember(companyId);
            if (companyMember != null) {
                Integer cType = companyMember.getCompanyType();
                if (CompanyGroupEnum.isGroup(cType)) {
                    // 登录公司为集团，则设置groupId = companyId
                    companyMember.setGroupId(companyMember.getCompanyId());
                }
                redisUtils.set(memberKey, companyMember);
            }
        }
        if (companyMember == null) {
            throw new OwnerException("不存在当前用户的公司信息");
        }
        return companyMember;
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
    public UserInfo getUserInfoByToken(String token, String companyId) throws OwnerException {
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
        if (redisUtils.hasKey(sessionKey)) {
            UserSession userSession = redisUtils.get(sessionKey, UserSession.class);
            Integer cId;
            if (null != userSession) {
                List<Integer> roleIds;
                if (userSession.getUserSource() == 0) {
                    String userUpdateKey = MessageFormat.format(Constant.REDIS_KEY_USER_UPDATE_TIME, userSession.getId());
                    Date userEditTime = redisUtils.get(userUpdateKey, Date.class);
                    // 该用户有修改记录，删除登录信息
                    if (null != userEditTime && userSession.getCreateTime().before(userEditTime)) {
                        redisUtils.del(sessionKey);
                        return null;
                    }
                    if (!StringUtils.isEmpty(companyId)) {
                        cId = Integer.parseInt(companyId);
                        //cId由前端传递，则检查当前用户是否登录过该公司，未登陆过，执行登出操作
                        String userLoginCompanyKey = MessageFormat.format(Constant.REDIS_USER_LOGIN_COMPANY_KEY, cId, token);
                        if (!redisUtils.hasKey(userLoginCompanyKey)) {
                            return null;
                        }
                        redisUtils.expire(userLoginCompanyKey, Constant.RS_USER_SESSION_TIMEOUT);
                    } else {
                        cId = userSession.getCompanyId();
                    }
                    roleIds = appUserRoleDao.getUserRoleIds(userSession.getId(), cId);
                } else {
                    if (StringUtils.isEmpty(companyId)) {
                        return null;
                    }
                    cId = Integer.parseInt(companyId);
                    String newKey = MessageFormat.format("MsMType:{0}_{1}", userSession.getId(), companyId);
                    List<ProductMtype> ProductMtypes = redisUtils.get(newKey, ArrayList.class);
                    if (!CollectionUtils.isEmpty(ProductMtypes)) {
                        roleIds = appUserRoleDao.getUserRoleByMTypes(ProductMtypes);
                    } else {
                        return null;
                    }
                }
                CompanyMember companyMember = getCompanyMember(cId, true);
                // 获取缓存时，不更替原登录的companyType
                userSession.copyCompanyInfo(companyMember);
                UserInfo userInfo = new UserInfo();
                BeanUtil.copyProperties(userSession, userInfo);
                List<RsMenuPermModel> permModels = new ArrayList<>();
                roleIds.forEach(roleId -> {
                    List<RsMenuPermModel> tempPermModels;
                    String roleKey = MessageFormat.format(Constant.REDIS_KEY_ROLE, roleId);
                    if (redisUtils.hasKey(roleKey)) {
                        tempPermModels = redisUtils.getAsList(roleKey, RsMenuPermModel.class);
                    } else {
                        tempPermModels = appRoleMenuDao.getPermData(roleId);
                        redisUtils.set(roleKey, tempPermModels, Constant.RS_USER_SESSION_TIMEOUT);
                    }
                    permModels.addAll(tempPermModels);
                });
                Map<String, RsMenuPermModel> permDataMap = new HashMap<>();
                permModels.forEach(item -> {
                    permDataMap.put(item.getPerms(), item);
                });
                userInfo.setPermDataMap(permDataMap);
                redisUtils.expire(sessionKey, userSession.getUserSource() == 1 ? Constant.MS_USER_SESSION_TIMEOUT : Constant.RS_USER_SESSION_TIMEOUT);
                return userInfo;
            }
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param pwdMode
     * @return
     */
    @Override
    public Boolean updatePassword(int userId, String userName, PwdMode pwdMode) {
        String oldPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, pwdMode.getOldPassword()));
        String newPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, pwdMode.getNewPassword()));
        int affectedRows = userDao.updatePassword(userId, oldPassword, newPassword);
        return affectedRows > 0;
    }

    /**
     * 登出
     *
     * @param token
     * @return
     */
    @Override
    public Boolean logout(String token) {
        if (StringUtils.hasLength(token)) {
            redisUtils.del(MessageFormat.format(Constant.REDIS_KEY_SESSION, token));
        }
        return true;
    }

    @Override
    public PageModel<List<User>> queryUserList(int companyId, QueryUserListModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> des = new ArrayList<>();
            des.add("createTime");
            page.setDescs(des);
        }
        return PageUtils.build(page, userDao.queryUserList(companyId, page, query));
    }

    @Override
    public boolean addChildUser(UserInfo info, ChildUserModel model) throws OwnerException {
        if (userDao.registerCheckUser(model.getUserName()) != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }
        User user = new User();
        BeanUtil.copyProperties(model, user);
        user.setType(1);
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword())));
        user.setCompanyId(info.getCompanyId());
        user.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
        user.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        user.setMsCreatorId(info.getUserSource() == 0 ? -1 : info.getId());
        user.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
        user.setLastUpdateTime(new Date());
        user.setCreateTime(new Date());
        return userDao.insert(user) > 0;
    }

    @Override
    public Boolean checkuserName(String userName, Integer id) {
        User user = userDao.registerCheckUser(userName);
        if (user == null) {
            return true;
        }
        if (user.getId().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public Integer registerCheckUser(String userName) {
        if (userName.length() == 0) {
            return 1;
        }
        User user = userDao.registerCheckUser(userName);
        if (user != null) {
            return 2;
        }
        return 0;
    }

    @Override
    public String jumpChild(CompanyMember companyMember, UserInfo userInfo) throws OwnerException {
        Integer childId = companyMember.getCompanyId();
        int count = companyDao.countChild(childId, userInfo.getGroupFullPath());
        if (count <= 0) {
            throw new OwnerException(MessageFormat.format("【{0}】不存在子公司【{1}】", userInfo.getCompanyName(), companyMember.getCompanyName()));
        }
        redisUtils.set(MessageFormat.format(Constant.REDIS_USER_LOGIN_COMPANY_KEY, childId, userInfo.getToken()), childId, Constant.RS_USER_SESSION_TIMEOUT);
        return "/?cId=" + childId;
    }

    @Override
    public boolean updateChildUser(UserInfo info, ChildUserModel model) {
        User user = new User();
        BeanUtil.copyProperties(model, user);
        user.setCompanyId(info.getCompanyId());
        user.setLastUpdateTime(new Date());
        user.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        user.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
        return userDao.updateById(user) > 0;
    }

    @Override
    public boolean delChilUser(ChildUserModel model) {
        return userDao.deleteById(model.getId()) > 0;
    }

    @Override
    public boolean updateChiluserPassword(UserInfo info, ChildUserModel model) {
        User user = new User();
        BeanUtil.copyProperties(model, user);
        user.setCompanyId(info.getCompanyId());
        user.setMsLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        user.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
        user.setLastUpdateTime(new Date());
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword())));
        return userDao.updateById(user) > 0;
    }

    @Override
    public Boolean register(RegisterModel registerModel) throws OwnerException {

        String userName = registerModel.getUserName();
        User userInfo = userDao.getUserInfo(userName);
        if (userInfo != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }

        String companyName = registerModel.getCompanyName();
        CompanyEntity companyInfo = companyDao.getCompanyInfo(companyName);
        if (companyInfo != null) {
            throw new OwnerException(ErrorEnum.COMPANYNAME_EXSIT);
        }

        Date date = new Date();

        CompanyEntity company = new CompanyEntity();

        company.setCompanyName(companyName);
        company.setInvoiceTitle(companyName);
        company.setCompanyAddress(registerModel.getCompanyAddress());
        company.setAddressCode("");
        company.setTaxpayerId("");
        company.setCreditCode("");
        String industryCodeString = String.join(",", registerModel.getIndustryCode());
        company.setIndustryCode(industryCodeString);
        company.setMainIndustry("");
        company.setLinkMan(registerModel.getLinkMan());
        company.setLinkTel(registerModel.getLinkTel());
        company.setOwner("");
        company.setCapital(0);
        company.setMembers(0);
        company.setDepts(0);
        company.setHasDevAccount(false);
        company.setHighTec(false);
        company.setBusinessLicense("");
        company.setLogo("");
        company.setCreateTime(date);
        company.setCreatorId(-1);
        company.setLastUpdateTime(date);
        company.setLastUpdatorId(-1);
        company.setFrom(0);
        company.setStatus(0);

        User user = new User();

        user.setUserName(userName);
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, registerModel.getPassword())));
        user.setRealName(companyName);
        user.setGender(0);
        user.setTel("");
        user.setStatus(0);
        user.setCreatorId(-1);
        user.setCreateTime(date);
        user.setLastUpdatorId(-1);
        user.setLastUpdateTime(date);
        user.setType(0);
        user.setMsLastUpdatorId(-1);
        user.setMsCreatorId(-1);
        Dept dept = new Dept();
        dept.setDeptName(companyName);
        dept.setParentId(-1);
        dept.setLevel(0);
        dept.setIdentity("");
        dept.setRemark("");
        dept.setCreatorId(-1);
        dept.setCreateTime(date);
        dept.setLastUpdatorId(-1);
        dept.setMsLastUpdatorId(-1);
        dept.setMsCreatorId(-1);
        dept.setLastUpdateTime(new Date());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            companyDao.insert(company);
            user.setCompanyId(company.getId());
            userDao.insert(user);
            dept.setCompanyId(company.getId());
            deptDao.insert(dept);
            dept.setFullPath(dept.getId() + Constant.PATH_SEPARATOR);
            deptDao.updateById(dept);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return true;
    }

//    /**
//     * 获取用户信息
//     *
//     * @param userId
//     * @param token
//     * @param avatar
//     * @param realName
//     * @param userName
//     * @return
//     */
//    private UserSession getMsUserSession(Integer userId, String token, String avatar, String realName, String userName, Integer defaultCompanyId, List<Integer> msUserIds) throws OwnerException {
//        List<CompanyMember> companyMemberList = new ArrayList<>();
//        CompanyMember companyMember = companyDao.getCompanyMember(defaultCompanyId);
//        UserSession userSession = new UserSession();
//        if (null != companyMember) {
//            companyMember.setSelected(true);
//            companyMemberList.add(companyMember);
//            if (CollectionUtils.isEmpty(msUserIds)) {
//                userSession = userDao.getMSUserSession(companyMember.getCompanyId(), userId);
//            } else {
//                userSession.setUserSource(1);
//                userSession.setStatus(0);
//                userSession.setMenuPermModelList(managerRoleDao.getMSUserPerms(defaultCompanyId, msUserIds));
//                userSession.setRoleIds(managerRoleDao.getRoles(defaultCompanyId, msUserIds));
//            }
//            userSession.setCompanyId(companyMember.getCompanyId());
//            userSession.setCompanyName(companyMember.getCompanyName());
//            userSession.setCompanyLogoPath(companyMember.getCompanyLogoPath());
//        } else {
//            userSession.setCompanyId(0);
//            userSession.setCompanyName(realName);
//            userSession.setUserSource(1);
//            userSession.setStatus(0);
//            userSession.setMenuPermModelList(new ArrayList<>());
//        }
//        userSession.setRealName(realName);
//        userSession.setUserName(userName);
//        userSession.setId(userId);
//        userSession.setAvatar(avatar);
//        Date now = new Date();
//        userSession.setUpdateTime(now);
//        userSession.setMemberList(companyMemberList);
//        userSession.setToken(token);
//        userSession.setExpireTime(DateUtil.addDays(now, msConfig.getLogin().getExpireTime()));
//        userSession.setCreateTime(now);
//        return userSession;
//    }

//    @Override
//    public String jumpLogin(LoginInfo loginInfo) throws OwnerException {
//        try {
//            String tempKey = "tempToken:" + loginInfo.getLoginTmpCode();
//            MsUserInfo msUserInfo = redisUtils.get(tempKey, MsUserInfo.class);
//            if (null == msUserInfo) {
//                throw new OwnerException("会话失效，请重试。");
//            }
//            redisUtils.del(tempKey);
//            String token = TokenGenerator.generateValue();
//            UserSession userSession = getMsUserSession(msUserInfo.getUserId(), token, msUserInfo.getPicUrl(), msUserInfo.getRealName(), msUserInfo.getUserName(), msUserInfo.getCurrentCompanyId(), msUserInfo.getMsUserIds());
//            UserInfoCache userInfoCache = new UserInfoCache();
//            userInfoCache.setCompanyName(userSession.getCompanyName());
//            userInfoCache.setCompanyLogoPath(userSession.getCompanyLogoPath());
//            userInfoCache.setCompanyId(userSession.getCompanyId());
//            userInfoCache.setId(userSession.getId());
//            userInfoCache.setUserName(msUserInfo.getUserName());
//            userInfoCache.setRealName(msUserInfo.getRealName());
//            userInfoCache.setAvatar(msUserInfo.getPicUrl());
//            userInfoCache.setUserSource(1);
//            userInfoCache.setMsUserIds(msUserInfo.getMsUserIds());
//            userInfoCache.setToken(token);
//            String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
//            String userKey = MessageFormat.format(Constant.REDIS_KEY_USER_INFO, userInfoCache.getCompanyId(), userInfoCache.getUserSource(), userInfoCache.getId());
//            redisUtils.set(sessionKey, userInfoCache, Constant.MS_USER_SESSION_TIMEOUT);
//            if (!redisUtils.checkedKey(userKey)) {
//                redisUtils.set(userKey, userSession, Constant.MS_USER_SESSION_TIMEOUT);
//            } else {
//                redisUtils.expire(userKey, Constant.MS_USER_SESSION_TIMEOUT);
//            }
//            sysLogDao.insert(SysLog.build(userInfoCache.getId(), userInfoCache.getUserSource(), userInfoCache.getUserName(), userInfoCache.getRealName(), "管理员登录", "/api/user/login", "用户名: " + userInfoCache.getUserName(), "", userInfoCache.getCompanyId(), userInfoCache.getCompanyName()));
//            return token;
//        } catch (OwnerException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            logger.error("msLogin", ex);
//            throw new OwnerException(ErrorEnum.FAIL);
//        }
//    }
}
