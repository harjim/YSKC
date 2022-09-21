package com.xxl.job.executor.models.wechat;

import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-24 10:39
 * @Description: 模板消息（订阅消息）
 */
public class TemplateMsgModel {

    private String touser;
    private String template_id;
    private String page;
    private Map<String,Map<String,Object>> data;

    public static TemplateMsgModel build(String openId,String template_id,String page,Map<String,Map<String,Object>> data){
        TemplateMsgModel template = new TemplateMsgModel();
        template.touser = openId;
        template.template_id = template_id;
        template.page = page;
        template.data = data;
        return template;

    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, Map<String, Object>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, Object>> data) {
        this.data = data;
    }
}
