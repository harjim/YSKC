package com.yskc.rs.models;

import com.yskc.common.model.RsMenuPermModel;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class UserInfo {
    private Integer id;
    private  String userName;
    private String realName;
    private String avatar;
    private Integer status;
    private Integer companyId;
    private String companyName;
    private String token;
    private List<Integer> roleIds;
    private String companyLogoPath;
    private Integer userSource;
    private Map<String, RsMenuPermModel> permDataMap;
    private Integer userId;
    private Integer msUserId;
    private Integer companyType;
    private Integer groupId;
    private String groupFullPath;
    /**
     * 管理的公司，从app_user_role表根据当前用户id所关联的公司id获取
     * 默认为companyId，userSource为0时，companyIds = List（companyId）
     */
    private List<Integer> companyIds;
    private Integer roleType;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Map<String, RsMenuPermModel> getPermDataMap() {
        return permDataMap;
    }

    public void setPermDataMap(Map<String, RsMenuPermModel> permDataMap) {
        this.permDataMap = permDataMap;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<Integer> getCompanyIds() {
        if(CollectionUtils.isEmpty(companyIds)){
            return Collections.singletonList(companyId);
        }
        return companyIds;
    }

    public void setCompanyIds(List<Integer> companyIds) {
        this.companyIds = companyIds;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
