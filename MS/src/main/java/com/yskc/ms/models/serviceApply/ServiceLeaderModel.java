package com.yskc.ms.models.serviceApply;

/**
 * @program: ms
 * @description: 查找高级人员
 * @author: cyj
 * @create: 2022-08-12 08:18
 **/
public class ServiceLeaderModel {
    private String roleName;
    private Integer userId;
    private String userName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
