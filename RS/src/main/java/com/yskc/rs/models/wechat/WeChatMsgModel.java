package com.yskc.rs.models.wechat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-27 09:37
 * @Description: 微信信息
 */
public class WeChatMsgModel {

    private Integer errcode;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
