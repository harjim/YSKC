package com.yskc.ms.models.company;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.company
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-05 10:32
 * @Description: 客户查询model
 */
public class QueryCompanyModel extends PageParams {
    private String companyName;
    private String linkMan;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }
}
