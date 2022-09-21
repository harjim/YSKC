package com.yskc.docservice.models;

import java.util.Map;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.models
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 13:50
 * @Description: 用户信息
 */
public class BaseUserInfo {
    private Integer id;
    private String userName;
    private String realName;
    private String avatar;
    private Integer status;
    /**
     * 登录类型：rs，ms
     */
    private String loginType;
    private Map<String,Boolean> permDataMap;
    private String token;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Map<String, Boolean> getPermDataMap() {
        return permDataMap;
    }

    public void setPermDataMap(Map<String, Boolean> permDataMap) {
        this.permDataMap = permDataMap;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
