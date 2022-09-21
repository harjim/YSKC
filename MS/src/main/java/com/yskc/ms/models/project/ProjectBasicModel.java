package com.yskc.ms.models.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/2 8:25
 * description:项目服务表数据model
 */
public class ProjectBasicModel implements Serializable {

    private Integer id;
    private Integer isImplemented;//是否标准化
    private BigDecimal income;//收入
    private BigDecimal incomeTax;//所得税
    private Integer totalStaff;//人数
    private BigDecimal researchFee;//预计研发费
    private BigDecimal taxRefiefs;//减免税
    private String techStaff;//技术人员情况
    private String financeStaff;//财务人员情况
    private String manager;//高层情况
    private Date startTime;//启动时间
    private Integer applyStatus;//高新是否申请
    private Boolean hasPayPatent;//有误专利费
    private String other;//其他
    private Integer projectId;
    private String companyLevel;//客户级别
    private Integer status;//状态
    private Integer owerId;
    private Integer deptId;
    private Integer customerId;

    private Date beginTime;//时间线开始

    private Date endTime;//时间线结束

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsImplemented() {
        return isImplemented;
    }

    public void setIsImplemented(Integer isImplemented) {
        this.isImplemented = isImplemented;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public Integer getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(Integer totalStaff) {
        this.totalStaff = totalStaff;
    }

    public BigDecimal getResearchFee() {
        return researchFee;
    }

    public void setResearchFee(BigDecimal researchFee) {
        this.researchFee = researchFee;
    }

    public BigDecimal getTaxRefiefs() {
        return taxRefiefs;
    }

    public void setTaxRefiefs(BigDecimal taxRefiefs) {
        this.taxRefiefs = taxRefiefs;
    }

    public String getTechStaff() {
        return techStaff;
    }

    public void setTechStaff(String techStaff) {
        this.techStaff = techStaff;
    }

    public String getFinanceStaff() {
        return financeStaff;
    }

    public void setFinanceStaff(String financeStaff) {
        this.financeStaff = financeStaff;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Boolean getHasPayPatent() {
        return hasPayPatent;
    }

    public void setHasPayPatent(Boolean hasPayPatent) {
        this.hasPayPatent = hasPayPatent;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
