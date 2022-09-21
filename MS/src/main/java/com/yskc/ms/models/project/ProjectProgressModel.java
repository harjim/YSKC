package com.yskc.ms.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 16:04
 * @Description: 项目进度model
 */
public class ProjectProgressModel implements Serializable {
    private Integer id;
    private Integer customerId;
    private Integer companyId;
    private String companyName;
    private String companyLevel;
    private String productName;
    private String deptName;
    private Integer year;
    private String techRealName;
    private String financeRealName;
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
    private Integer employeeOpenidCnt;
    private Integer equipmentCount;
    private Integer rdEquipmentCount;
    private Integer rdDeptLevel;
    private String rdDeptLevelStr;
    private String owerName;//业务员
    private Integer reportNum;//项目立项报告数量
    private Integer doneTotal;
    private Integer ongoingTotal;
    private Integer auditTotal;
    private Integer lastRdCnt;
    private Integer patentApplyCnt;
    private Integer buildCnt;
    private String types;
    private String typesStr;
    private String groupName;
    private Integer groupId;

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public String getRdDeptLevelStr() {
        return rdDeptLevelStr;
    }

    public void setRdDeptLevelStr(String rdDeptLevelStr) {
        this.rdDeptLevelStr = rdDeptLevelStr;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
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

    public Integer getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Integer doneTotal) {
        this.doneTotal = doneTotal;
    }

    public Integer getOngoingTotal() {
        return ongoingTotal;
    }

    public void setOngoingTotal(Integer ongoingTotal) {
        this.ongoingTotal = ongoingTotal;
    }

    public Integer getAuditTotal() {
        return auditTotal;
    }

    public void setAuditTotal(Integer auditTotal) {
        this.auditTotal = auditTotal;
    }

    public void addAuditTotal(Integer auditTotal) {
        if (null == this.auditTotal) {
            this.auditTotal = 0;
        }
        this.auditTotal += auditTotal;
    }

    public Integer getLastRdCnt() {
        return lastRdCnt;
    }

    public void setLastRdCnt(Integer lastRdCnt) {
        this.lastRdCnt = lastRdCnt;
    }

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }

    public Integer getBuildCnt() {
        return buildCnt;
    }

    public void setBuildCnt(Integer buildCnt) {
        this.buildCnt = buildCnt;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTypesStr() {
        return typesStr;
    }

    public void setTypesStr(String typesStr) {
        this.typesStr = typesStr;
    }

    public Integer getEmployeeOpenidCnt() {
        return employeeOpenidCnt;
    }

    public void setEmployeeOpenidCnt(Integer employeeOpenidCnt) {
        this.employeeOpenidCnt = employeeOpenidCnt;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
