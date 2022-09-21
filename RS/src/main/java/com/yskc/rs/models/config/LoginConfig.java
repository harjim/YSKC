package com.yskc.rs.models.config;

/**
 * 登录配置
 * @author huronghua
 */
public class LoginConfig {
    /**
     * 登录过期时间(天)
     */
    private int expireTime;

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
