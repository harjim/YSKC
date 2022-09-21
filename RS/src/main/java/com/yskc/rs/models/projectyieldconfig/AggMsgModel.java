package com.yskc.rs.models.projectyieldconfig;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 13:36
 * @Description: 归集信息
 */
public class AggMsgModel {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AggMsgModel() {
    }

    public AggMsgModel(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static AggMsgModel buildFail(String msg) {
        return new AggMsgModel(false, msg);
    }

    public static AggMsgModel buildSuccess(String msg) {
        return new AggMsgModel(true, msg);

    }
}
