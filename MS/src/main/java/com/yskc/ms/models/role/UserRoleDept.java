package com.yskc.ms.models.role;

import java.util.List;

public class UserRoleDept {
    private Integer userId;
    private Integer roleId;
    private List<Integer> deptIds;
    private List<String> fullPaths;

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

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<String> getFullPaths() {
        return fullPaths;
    }

    public void setFullPaths(List<String> fullPaths) {
        this.fullPaths = fullPaths;
    }
}
