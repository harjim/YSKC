package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.User;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.user.ChildUserModel;
import com.yskc.ms.models.user.QueryRsUserModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-27 09:31
 * @Description: 账户管理接口类
 */
public interface RsUserService {

    /**
     * 获取所用用户信息
     *
     * @param query
     * @return
     */
    PageModel<List<User>> queryUserList(QueryRsUserModel query);


    /**
     * 添加
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean addUser(UserInfo info, ChildUserModel model) throws OwnerException;

    /**
     * 修改
     *
     * @param model
     * @return
     */
    boolean updateUser(UserInfo info, ChildUserModel model);

    /**
     * 删除
     *
     * @param model
     * @return
     */
    boolean delUser(ChildUserModel model);

    /**
     * 重置密码
     *
     * @param model
     * @return
     */
    boolean updatePassword(UserInfo info, ChildUserModel model);

    /**
     * @param userName
     * @return
     */
    boolean checkuserName(String userName);


}
