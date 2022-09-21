package com.yskc.rs.models.companyrdsummary;

import com.yskc.rs.models.params.PageParams;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.companyrdsummary
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-05 08:43
 * @Description: 查询公司研发汇总model
 */
public class QueryCompanyRdSummaryModel extends PageParams {

    private Integer year;

    private String companyName;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
