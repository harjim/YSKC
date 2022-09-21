package com.yskc.ms.models.project;

/**
 * Created by hck
 * on 2020/11/12 14:38
 * description:立项报告数量
 */
public class ProjectReportModel {

    private Integer companyId;

    private Integer year;

    private Integer reportNum;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }
}
