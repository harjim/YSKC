package com.yskc.docservice.models.rs;

import com.yskc.docservice.models.BaseUserInfo;

import java.util.List;

public class RsUserInfo extends BaseUserInfo {
    private Integer companyId;
    private String companyName;
    private List<Integer> roleIds;
    private String companyLogoPath;
    private Integer userSource;
    private Integer userId;
    private Integer msUserId;
    private Integer companyType;
    private Integer groupId;
    private String groupFullPath;

    public Integer getUserId() {
        return userSource == 0 ? getId() : -1;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMsUserId() {
        return userSource == 0 ? -1 : getId();
    }

    public void setMsUserId(Integer msUserId) {
        this.msUserId = msUserId;
    }

    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupFullPath() {
        return groupFullPath;
    }

    public void setGroupFullPath(String groupFullPath) {
        this.groupFullPath = groupFullPath;
    }
}
