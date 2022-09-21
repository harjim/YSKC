package com.yskc.ms.models.projectsummary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/10 14:37
 * description:
 */
public class SummaryDataModel {
    private Integer customerId;
    private Integer companyId;
    private Integer year;
    private String companyName;
    private String productName;

    private Integer cnt;
    private Integer rdCount;
    private BigDecimal budget;
    private BigDecimal rdFunds;
    private Integer docFileCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;
    private String operationRealName;
    private Integer employeeCount;
    private Integer rdEmployeeCount;
    private Integer equipmentCount;
    private Integer rdEquipmentCount;
    private Integer rdDeptLevel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getOperationRealName() {
        return operationRealName;
    }

    public void setOperationRealName(String operationRealName) {
        this.operationRealName = operationRealName;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Integer getRdEquipmentCount() {
        return rdEquipmentCount;
    }

    public void setRdEquipmentCount(Integer rdEquipmentCount) {
        this.rdEquipmentCount = rdEquipmentCount;
    }

    public Integer getRdDeptLevel() {
        return rdDeptLevel;
    }

    public void setRdDeptLevel(Integer rdDeptLevel) {
        this.rdDeptLevel = rdDeptLevel;
    }
}
