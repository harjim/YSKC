package com.yskc.ms.entity.rs.models;

import com.alibaba.druid.sql.visitor.functions.Bin;

import java.math.BigDecimal;

public class SummaryModel {
    private Integer id;
    private Integer companyId;
    private Integer beginYear;
    private Integer endYear;
    private BigDecimal summary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public BigDecimal getSummary() {
        return summary;
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }
}
