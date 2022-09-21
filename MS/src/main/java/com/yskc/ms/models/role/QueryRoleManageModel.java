package com.yskc.ms.models.role;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/17 11:51
 * description:角色管理/用户列表
 */
public class QueryRoleManageModel extends PageParams {
    private String realName;
    private Integer roleId;
    private String fullPath;
    private Integer status;
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
