package com.yskc.rs.models.hightech;

import java.util.Date;

/**
 * @program: rs
 * @description: 高新进度获取辅助账
 * @author: cyj
 * @create: 2022-05-23 16:13
 **/
public class QueryAssistModel {
    private Integer companyId;
    private Integer projectId;
    private Integer year;
    private Date month;
    private String rdTitle;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }
}
