package com.yskc.ms.models.policy;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.policy
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:39
 * @Description: 政策来源model
 */
public class PolicySourceModel {
    private Integer id;
    private String sname;
    private String domain;
    private Boolean subscription;
    private String stype;
    private String typeUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }
}
