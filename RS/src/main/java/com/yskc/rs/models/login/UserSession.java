package com.yskc.rs.models.login;

import com.yskc.rs.models.company.CompanyMember;

import java.util.Date;
import java.util.List;

/**
 * 用户会话信息
 *
 * @author huronghua
 */
public class UserSession {
    private Integer id;
    private String userName;
    private String realName;
    private String avatar;
    private String token;
    private Date createTime;
    private Integer companyId;
    private String companyName;
    private Integer status;
    private Integer userSource;
    private List<Integer> roleIds;
    private String companyLogoPath;
    private Integer companyType;
    private Integer groupId;
    private String groupFullPath;
    private Integer roleType;
    /**
     * 管理的公司，从app_user_role表根据当前用户id所关联的公司id获取
     * 默认为companyId，userSource为0时，companyIds = List（companyId）
     */
    private List<Integer> companyIds;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public void copyCompanyInfo(CompanyMember companyMember) {
        this.companyId = companyMember.getCompanyId();
        this.companyName = companyMember.getCompanyName();
        this.companyLogoPath = companyMember.getCompanyLogoPath();
        this.groupId = companyMember.getGroupId();
        this.groupFullPath = companyMember.getFullPath();
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

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public List<Integer> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<Integer> companyIds) {
        this.companyIds = companyIds;
    }
}
