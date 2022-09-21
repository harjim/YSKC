package com.xxl.job.executor.models.projectsummary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-26 13:55
 * @Description: 项目汇总model
 */
public class ProjectSummaryModel {
    private Integer year;
    private Integer companyId;
    private Integer projectId;
    private Integer customerId;
    private String fullPath;
    /**
     * 规划研发数
     */
    private Integer cnt;
    /**
     * 财务核算类型
     */
    private Integer accountType;
    /**
     * 财务规划研发费(万元)
     */
    private Integer rdFee;
    /**
     * 人员总数(录入)
     */
    private Integer employeeAmount;
    /**
     * 营收预测（万元）
     */
    private Integer revenueFcst;

    private Integer rdCount;
    private BigDecimal budget;
    private BigDecimal rdFunds;
    private Integer docFileCount;
    private Date operationTime;
    private Integer operationUserId;
    private Integer employeeCount;
    private Integer equipmentCount;
    private Integer rdEmployeeCount;
    private Integer employeeOpenidCnt;
    private Integer rdEquipmentCount;
    private Integer patentApplyCnt;
    private Integer lastRdCnt;
    private Integer nextRdCnt;
    private Integer reportCnt;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
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

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Integer operationUserId) {
        this.operationUserId = operationUserId;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
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

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }

    public Integer getLastRdCnt() {
        return lastRdCnt;
    }

    public void setLastRdCnt(Integer lastRdCnt) {
        this.lastRdCnt = lastRdCnt;
    }

    public Integer getEmployeeOpenidCnt() {
        return employeeOpenidCnt;
    }

    public void setEmployeeOpenidCnt(Integer employeeOpenidCnt) {
        this.employeeOpenidCnt = employeeOpenidCnt;
    }

    public Integer getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(Integer reportCnt) {
        this.reportCnt = reportCnt;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public Integer getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(Integer revenueFcst) {
        this.revenueFcst = revenueFcst;
    }

    public Integer getNextRdCnt() {
        return nextRdCnt;
    }

    public void setNextRdCnt(Integer nextRdCnt) {
        this.nextRdCnt = nextRdCnt;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
