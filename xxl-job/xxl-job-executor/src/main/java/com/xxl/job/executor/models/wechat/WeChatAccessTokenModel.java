package com.xxl.job.executor.models.wechat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-27 09:43
 * @Description: accessTokenModel
 */
public class WeChatAccessTokenModel extends WeChatMsgModel {

    private String access_token;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "WeChatAccessTokenModel{" +
                "access_token='" + access_token + '\'' +
                ", errcode='" + getErrcode() + '\'' +
                ", errmsg='" + getErrmsg() + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }

}
