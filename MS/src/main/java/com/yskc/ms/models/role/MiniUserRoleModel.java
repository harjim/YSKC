package com.yskc.ms.models.role;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-20 11:29
 * @Description:
 */
public class MiniUserRoleModel implements Serializable {


    private Integer userId;

    private Integer roleId;

    private String roleName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
