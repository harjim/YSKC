package com.yskc.ms.models.techsummary;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.techsummary
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-15 09:19
 * @Description: 查询技改汇总model
 */
public class QueryTechSummaryModel extends PageParams {
    private Integer year;
    private String fullPath;
    private String companyName;;
    private Integer[] tIds;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }
}
