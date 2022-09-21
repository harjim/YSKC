package com.yskc.rs.models.companyrdsummary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.companyrdsummary
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-05 08:39
 * @Description: 公司研发汇总model
 */
public class CompanyRdSummaryModel {

    private Integer id;

    private Integer companyType;

    private String companyName;
    /**
     * rd规划数(report 表)
     */
    private Integer rdPlanCount;
    /**
     * rd数
     */
    private Integer rdCount;
    /**
     * 规划研发费(report 表)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal budget;
    /**
     * 研发费用
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdFunds;
    /**
     * 研发人员数
     */
    private Integer rdEmployeeCount;
    /**
     * 研发设备数
     */
    private Integer rdEquipmentCount;
    /**
     * 过程文档数
     */
    private Integer docFileCount;
    /**
     * 研发机构建设数：只统计已上传附件的机构建设
     */
    private Integer buildCount;
    /**
     * 高品数
     */
    private Integer highTechCount;
    /**
     * 高品费用
     */
    private BigDecimal highTechIncome;
    /**
     * 累计主营业务收入
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalIncome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getRdPlanCount() {
        return rdPlanCount;
    }

    public void setRdPlanCount(Integer rdPlanCount) {
        this.rdPlanCount = rdPlanCount;
    }

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
    }

    public Integer getRdEquipmentCount() {
        return rdEquipmentCount;
    }

    public void setRdEquipmentCount(Integer rdEquipmentCount) {
        this.rdEquipmentCount = rdEquipmentCount;
    }

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Integer getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }

    public Integer getHighTechCount() {
        return highTechCount;
    }

    public void setHighTechCount(Integer highTechCount) {
        this.highTechCount = highTechCount;
    }

    public BigDecimal getHighTechIncome() {
        return highTechIncome;
    }

    public void setHighTechIncome(BigDecimal highTechIncome) {
        this.highTechIncome = highTechIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}
