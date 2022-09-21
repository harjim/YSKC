package com.yskc.ms.models.patent;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: wangxing
 * @CreateTime: 2019-11-08 16:03
 * @Description: 查询model
 */
public class PcompanyModel {
    private  Integer companyId;
    private  String companyName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
