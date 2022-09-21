package com.yskc.ms.models.techrequirement;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.techrequirement
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:33
 * @Description: 查询技术需求
 */
public class QueryTechRequirement extends PageParams {

    private String companyName;

    private String techName;

    private String linkName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
}
