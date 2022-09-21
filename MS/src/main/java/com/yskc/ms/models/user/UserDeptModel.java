package com.yskc.ms.models.user;

import java.util.List;

public class UserDeptModel {
    private Integer id;
    private String[] deptIdArr;
    private List<UserDeptModel> userList;
    private String dingUserId;
    private String unionid;
    private Integer userId;
    private Integer deptId;
    private String deptName;
    private String fullPath;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getDeptIdArr() {
        return deptIdArr;
    }

    public void setDeptIdArr(String[] deptIdArr) {
        this.deptIdArr = deptIdArr;
    }

    public List<UserDeptModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDeptModel> userList) {
        this.userList = userList;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
