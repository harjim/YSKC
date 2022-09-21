package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.ProductMtype;
import com.yskc.common.model.RsMenuPermModel;
import com.yskc.common.utils.RedisUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.RsAppUserRoleDao;
import com.yskc.docservice.dao.rs.company.CompanyDao;
import com.yskc.docservice.enums.CompanyGroupEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.company.CompanyMember;
import com.yskc.docservice.models.rs.login.RsUserSession;
import com.yskc.docservice.service.rs.RsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 13:46
 * @Description: rs用户serviceImpl
 */
@Service
public class RsUserServiceImpl implements RsUserService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RsAppUserRoleDao rsAppUserRoleDao;
    @Autowired
    private CompanyDao companyDao;


    @Override
    public RsUserInfo getUserInfoByToken(String token, String companyId)throws OwnerException {
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_RS_SESSION, token);
        if (redisUtils.hasKey(sessionKey)) {
            RsUserSession userSession = redisUtils.get(sessionKey, RsUserSession.class);
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
                        redisUtils.expire(userLoginCompanyKey, Constant.ONE_DAY_TIME);
                    } else {
                        cId = userSession.getCompanyId();
                    }
                    roleIds = rsAppUserRoleDao.getUserRoleIds(userSession.getId(), cId);
                } else {
                    if (StringUtils.isEmpty(companyId)) {
                        return null;
                    }
                    cId = Integer.parseInt(companyId);
                    String newKey = MessageFormat.format("MsMType:{0}_{1}", userSession.getId(), companyId);
                    List<ProductMtype> ProductMtypes = redisUtils.get(newKey, ArrayList.class);
                    if (!CollectionUtils.isEmpty(ProductMtypes)) {
                        roleIds = rsAppUserRoleDao.getUserRoleByMTypes(ProductMtypes);
                    } else {
                        return null;
                    }
                }
                CompanyMember companyMember = getCompanyMember(cId, true);
                // 获取缓存时，不更替原登录的companyType
                userSession.copyCompanyInfo(companyMember);
                RsUserInfo userInfo = new RsUserInfo();
                BeanUtil.copyProperties(userSession, userInfo);
                List<RsMenuPermModel> permModels = new ArrayList<>();
                roleIds.forEach(roleId -> {
                    List<RsMenuPermModel> tempPermModels;
                    String roleKey = MessageFormat.format(Constant.REDIS_KEY_RS_ROLE, roleId);
                    if (redisUtils.hasKey(roleKey)) {
                        tempPermModels = redisUtils.getAsList(roleKey, RsMenuPermModel.class);
                    } else {
                        tempPermModels = rsAppUserRoleDao.getPermData(roleId);
                        redisUtils.set(roleKey, tempPermModels, Constant.ONE_DAY_TIME);
                    }
                    permModels.addAll(tempPermModels);
                });
                Map<String, Boolean> permDataMap = new HashMap<>();
                permModels.forEach(item -> {
                    permDataMap.put(item.getPerms(), true);
                });
                userInfo.setPermDataMap(permDataMap);
                redisUtils.expire(sessionKey, userSession.getUserSource() == 1 ? Constant.MS_USER_SESSION_TIMEOUT : Constant.ONE_DAY_TIME);
                return userInfo;
            }
        }
        return null;
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
}
