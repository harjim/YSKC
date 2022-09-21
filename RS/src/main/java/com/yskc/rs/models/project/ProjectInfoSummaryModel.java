package com.yskc.rs.models.project;

import com.yskc.rs.config.Constant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/3/8 9:01
 * @Description:
 */
public class ProjectInfoSummaryModel implements Serializable {

    private Integer projectId;

    private String rdTitle;

    private BigDecimal budget;//预算

    private BigDecimal staffRdHour;//研发人员工时

    private BigDecimal prodRdHour;//仪器设备工时
    /**
     * 研发材料
     */
    private BigDecimal materialTotal;
    /**
     * 修理费用
     */
    private BigDecimal repairTotal;
    /**
     * 试制费用
     */
    private BigDecimal trialTotal;
    private BigDecimal yieldAmount;//排期表

    private BigDecimal summary;//归集总表

    private BigDecimal budgetCost;//决算

    private String pname;//项目名称

    private Integer beginYear;

    private Integer endYear;

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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public ProjectInfoSummaryModel() {
    }

    public ProjectInfoSummaryModel(BigDecimal budget, BigDecimal staffRdHour,
                                   BigDecimal prodRdHour, BigDecimal materialTotal,
                                   BigDecimal repairTotal, BigDecimal trialTotal,
                                   BigDecimal yieldAmount, BigDecimal summary, BigDecimal budgetCost) {
        this.budget = budget;
        this.staffRdHour = staffRdHour;
        this.prodRdHour = prodRdHour;
        this.materialTotal = materialTotal; //.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
        this.repairTotal = repairTotal; //.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
        this.trialTotal = trialTotal; //.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
        this.yieldAmount = yieldAmount;
        this.summary = summary;
        this.budgetCost = budgetCost;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getStaffRdHour() {
        return staffRdHour;
    }

    public void setStaffRdHour(BigDecimal staffRdHour) {
        this.staffRdHour = staffRdHour;
    }

    public BigDecimal getProdRdHour() {
        return prodRdHour;
    }

    public void setProdRdHour(BigDecimal prodRdHour) {
        this.prodRdHour = prodRdHour;
    }


    public BigDecimal getYieldAmount() {
        return yieldAmount;
    }

    public void setYieldAmount(BigDecimal yieldAmount) {
        this.yieldAmount = yieldAmount;
    }

    public BigDecimal getSummary() {
        return summary;
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    public BigDecimal getBudgetCost() {
        return budgetCost;
    }

    public void setBudgetCost(BigDecimal budgetCost) {
        this.budgetCost = budgetCost;
    }

    public BigDecimal getMaterialTotal() {
        return materialTotal;
    }

    public void setMaterialTotal(BigDecimal materialTotal) {
        this.materialTotal = materialTotal;
    }

    public BigDecimal getRepairTotal() {
        return repairTotal;
    }

    public void setRepairTotal(BigDecimal repairTotal) {
        this.repairTotal = repairTotal;
    }

    public BigDecimal getTrialTotal() {
        return trialTotal;
    }

    public void setTrialTotal(BigDecimal trialTotal) {
        this.trialTotal = trialTotal;
    }
}
