package com.yskc.ms.models.dingding;

import java.util.List;

/**
 * 钉钉用户信息
 * 
 * @author huronghua
 */
public class DingInfo {
    private List<DingUser> dingUsers;
    List<Department> departments;

    public List<DingUser> getDingUsers() {
        return dingUsers;
    }

    public void setDingUsers(List<DingUser> dingUsers) {
        this.dingUsers = dingUsers;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
}
