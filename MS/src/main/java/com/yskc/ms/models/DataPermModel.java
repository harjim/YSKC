package com.yskc.ms.models;

import java.util.List;

public class DataPermModel {
    private Integer userId;
    private List<String> deptPaths;
    /**
     * 0:个人权限,1:部门权限,2:公司权限
     */
    private Integer permType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getDeptPaths() {
        return deptPaths;
    }

    public void setDeptPaths(List<String> deptPaths) {
        this.deptPaths = deptPaths;
    }

    public Integer getPermType() {
        return permType;
    }

    public void setPermType(Integer permType) {
        this.permType = permType;
    }
}
