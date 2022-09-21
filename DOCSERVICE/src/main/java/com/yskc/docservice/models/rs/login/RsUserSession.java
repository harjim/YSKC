package com.yskc.docservice.models.rs.login;

import com.yskc.docservice.models.rs.company.CompanyMember;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.models.rs.login
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 09:29
 * @Description: rs用户session
 */
public class RsUserSession {

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

}
