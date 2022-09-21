package com.yskc.ms.models.customer;

import java.math.BigDecimal;

public class CustomerProgressModel {

    private Integer companyId;
    private Integer year;
    /**
     * 立项数(p_report 的 cnt)
     */
    private Integer cnt;
    /**
     * 已立项数(p_project的总数)
     */
    private Integer projectNum;
    /**
     * 总预算(p_project的estimateExpense总数)
     */
    private BigDecimal budget;
    /**
     * 归集费用(p_summary表费用)
     */
    private BigDecimal summary;
    /**
     * 过程材料数(p_docFile数量)
     */
    private Integer docFileNum;

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

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(Integer projectNum) {
        this.projectNum = projectNum;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getSummary() {
        return summary;
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    public Integer getDocFileNum() {
        return docFileNum;
    }

    public void setDocFileNum(Integer docFileNum) {
        this.docFileNum = docFileNum;
    }
}
