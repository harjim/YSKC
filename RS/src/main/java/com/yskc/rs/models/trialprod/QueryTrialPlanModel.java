package com.yskc.rs.models.trialprod;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * Created by hck
 * on 2020/6/16 18:03
 * description:查询试制计划参数model
 */
public class QueryTrialPlanModel extends PageParams {

    private Date month;//月份

    private Integer year;

    private Date monthFirstDay;

    private Date monthLastDay;

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Date getMonthFirstDay() {
        return monthFirstDay;
    }

    public void setMonthFirstDay(Date monthFirstDay) {
        this.monthFirstDay = monthFirstDay;
    }

    public Date getMonthLastDay() {
        return monthLastDay;
    }

    public void setMonthLastDay(Date monthLastDay) {
        this.monthLastDay = monthLastDay;
    }
}
