package com.yskc.ms.models.role;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-27 15:21
 * @Description: 批量设置角色
 */
public class BatchDeployRoleModel implements Serializable {

    private Integer companyId;

    private List<Integer> roleIds;

    private List<UserRoleModel> userList;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<UserRoleModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserRoleModel> userList) {
        this.userList = userList;
    }
}
