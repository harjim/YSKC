package com.yskc.docservice.models;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.models
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 16:57
 * @Description: 日志model
 */
public class LogModel {

    private String desc;
    private String url;
    private String params;
    private String ip;

    public LogModel() {
    }

    public LogModel(String desc, String url, String params, String ip) {
        this.desc = desc;
        this.url = url;
        this.params = params;
        this.ip = ip;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
