package com.yskc.ms.models.rs;

import com.yskc.ms.entity.ms.User;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-15 11:44
 * @Description: 管理服务人员角色
 */
public class ManagerUserRoleModel implements Serializable {

    private Integer companyId;
    private List<User> users;
    private List<Integer> roleIds;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
