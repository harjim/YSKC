package com.yskc.rs.models.projectyieldconfig;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

public class QueryYieldConfigParams extends PageParams {

    private String deptName;

    private Integer projectId;

    private Date month;

    private Date trialDate;

    private Integer year;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
