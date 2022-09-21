package com.yskc.rs.models.role;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-02 10:52
 * @Description: 添加角色model
 */
public class AddUserRoleModel implements Serializable {

    @NotNull(message = "获取用户信息失败")
    private Integer userId;
    private List<Integer> roleIds;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
