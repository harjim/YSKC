package com.yskc.rs.models.techrequirement;

import com.yskc.rs.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.techrequirement
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:33
 * @Description: 查询技术需求
 */
public class QueryTechRequirement extends PageParams {


    private String techName;

    private String linkName;

    private Integer year;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
