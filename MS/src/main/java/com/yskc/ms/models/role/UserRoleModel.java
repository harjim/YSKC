package com.yskc.ms.models.role;

import java.util.List;

/**
 * 用户权限
 *
 * @author huronghua
 */
public class UserRoleModel {
    private Integer userId;
    private Integer companyId;
    private List<Integer> companyIds;
    private List<Integer> roleIds;
    private List<Integer> userIds;
    private List<Integer> pageRoleIds;
    /**
     * 插入客户服务人员
     */
    private String realName;
    /**
     * 插入客户服务人员
     */
    private String userName;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Integer> getPageRoleIds() {
        return pageRoleIds;
    }

    public void setPageRoleIds(List<Integer> pageRoleIds) {
        this.pageRoleIds = pageRoleIds;
    }

    public List<Integer> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<Integer> companyIds) {
        this.companyIds = companyIds;
    }
}
