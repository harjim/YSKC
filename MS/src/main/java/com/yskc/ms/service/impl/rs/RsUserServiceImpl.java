package com.yskc.ms.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.rs.RsUserDao;
import com.yskc.ms.entity.rs.User;
import com.yskc.ms.enums.UserStatusEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.user.ChildUserModel;
import com.yskc.ms.models.user.QueryRsUserModel;
import com.yskc.ms.service.rs.RsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-27 09:32
 * @Description: 账户管理实现类
 */
@Service
public class RsUserServiceImpl implements RsUserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsUserDao rsUserDao;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public PageModel<List<User>> queryUserList(QueryRsUserModel query) {
        Pagination page = query.getPagination();
        return PageUtils.build(page, rsUserDao.queryUserList(page, query));
    }

    @Override
    public boolean addUser(UserInfo info, ChildUserModel model) throws OwnerException {
        if (rsUserDao.getUserByTerm(model.getCompanyId(), model.getUserName()) != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }
        User user = new User();
        BeanUtil.copyProperties(model, user);
        user.setType(1);
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword())));
        user.setCompanyId(model.getCompanyId());
        user.setCreatorId(info.getId());
        user.setLastUpdatorId(info.getId());
        user.setLastUpdateTime(new Date());
        user.setCreateTime(new Date());
        return rsUserDao.insert(user) > 0;
    }

    @Override
    public boolean updateUser(UserInfo info, ChildUserModel model) {
        User user = new User();
        User rsUser = rsUserDao.selectById(model.getId());
        BeanUtil.copyProperties(model, user);
        user.setCompanyId(rsUser.getCompanyId());
        user.setLastUpdateTime(new Date());
        user.setLastUpdatorId(model.getId());
        if (user.getStatus() == UserStatusEnum.DISABLED.getType()) {
            redisUtils.set(MessageFormat.format(Constant.REDIS_KEY_RS_USER_UPDATE_TIME, user.getId()), new Date(), Constant.ONE_DAY_TIME);
        }
        return rsUserDao.updateById(user) > 0;
    }

    @Override
    public boolean delUser(ChildUserModel model) {
        User user = rsUserDao.selectById(model.getId());
        if (user != null) {
            redisUtils.set(MessageFormat.format(Constant.REDIS_KEY_RS_USER_UPDATE_TIME, user.getId()), new Date(), Constant.ONE_DAY_TIME);
        }
        return rsUserDao.deleteById(model.getId()) > 0;
    }

    @Override
    public boolean updatePassword(UserInfo info, ChildUserModel model) {
        User user = new User();
        BeanUtil.copyProperties(model, user);
        user.setCompanyId(model.getCompanyId());
        user.setCreatorId(info.getId());
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword())));
        return rsUserDao.updateById(user) > 0;
    }

    @Override
    public boolean checkuserName(String userName) {
        User user = rsUserDao.getUserInfo(userName);
        if (user == null) {
            return true;
        }
        return false;
    }
}
