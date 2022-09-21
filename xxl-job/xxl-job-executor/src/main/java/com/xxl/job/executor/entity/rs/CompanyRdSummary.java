package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-02 10:07
 * @Description: 公司研发汇总相关
 */
@TableName("c_rd_summary")
public class CompanyRdSummary {

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer year;
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
    private BigDecimal budget;
    /**
     * 研发费用
     */
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
    private BigDecimal totalIncome;
    private Date createTime;
    private Date lastUpdateTime;

    public static CompanyRdSummary buildByHighTech(int curYear, Integer companyId) {
        CompanyRdSummary companyRdSummary = new CompanyRdSummary();
        companyRdSummary.year = curYear;
        companyRdSummary.companyId = companyId;
        companyRdSummary.highTechCount = 0;
        return companyRdSummary;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void addHighTechCount() {
        this.highTechCount += 1;
    }

    public void setCreate(Date now) {
        this.createTime = now;
        this.lastUpdateTime = now;
    }

    public void addData(CompanyRdSummary item) {
        if(null == this.rdPlanCount && null != item.rdPlanCount){
            this.rdPlanCount = item.rdPlanCount;
        }
        if(null == this.rdCount && null != item.rdCount){
            this.rdCount = item.rdCount;
        }
        if(null == this.budget && null != item.budget){
            this.budget = item.budget;
        }
        if(null == this.rdFunds && null != item.rdFunds){
            this.rdFunds = item.rdFunds;
        }
        if(null == this.rdEmployeeCount && null != item.rdEmployeeCount){
            this.rdEmployeeCount = item.rdEmployeeCount;
        }
        if(null == this.rdEquipmentCount && null != item.rdEquipmentCount){
            this.rdEquipmentCount = item.rdEquipmentCount;
        }
        if(null == this.docFileCount && null != item.docFileCount){
            this.docFileCount = item.docFileCount;
        }
        if(null == this.buildCount && null != item.buildCount){
            this.buildCount = item.buildCount;
        }
        if(null == this.highTechCount && null != item.highTechCount){
            this.highTechCount = item.highTechCount;
        }
        if(null == this.highTechIncome && null != item.highTechIncome){
            this.highTechIncome = item.highTechIncome;
        }
        if(null == this.totalIncome && null != item.totalIncome){
            this.totalIncome = item.totalIncome;
        }
    }
}
