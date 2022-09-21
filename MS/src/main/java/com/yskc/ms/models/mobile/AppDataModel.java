package com.yskc.ms.models.mobile;

import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.mobile
 * @Author: zhangdingfu
 * @CreateTime: 2022-06-08 11:10
 * @Description: 小程序数据model
 */
public class AppDataModel {

    private String request_id;
    private Integer errcode;
    private String errmsg;
    private Map<String,Object> result;
    private Map<String,Object> user_info;

    public boolean success(){
        return  errcode == 0;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

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

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getUser_info() {
        return user_info;
    }

    public void setUser_info(Map<String, Object> user_info) {
        this.user_info = user_info;
    }
}
