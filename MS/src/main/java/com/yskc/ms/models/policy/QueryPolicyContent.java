package com.yskc.ms.models.policy;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.policy
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-25 09:27
 * @Description: 查询model
 */
public class QueryPolicyContent extends PageParams {

    private String title;

    private String sname;

    private String addressCode;

    private Boolean subscription;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Boolean getSubscription() {
        return subscription;
    }

    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }
}
