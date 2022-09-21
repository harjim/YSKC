package com.yskc.ms.models.login;

/**
 * 登录配置
 * @author huronghua
 */
public class LoginConfig {
    /**
     * 登录过期时间(天)
     */
    private int expireTime;
    private String adminName;
    private String jumpUrl;

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
