package com.yskc.ms.entity.ms.models;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms.models
 * @Author: wangxing
 * @CreateTime: 2019-12-18 15:24
 * @Description: modl
 */
public class QueryCustomerAccountModel extends PageParams {
    private String platform;
    private String pUrl;
    private String addressCode;
    private String companyName;


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
