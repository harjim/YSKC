package com.yskc.rs.models.reportform;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: rs
 * @description:项目总指标model
 * @author: cyj
 * @create: 2022-01-19 16:21
 **/
public class TotalIndexModel implements Serializable {
    private Integer accountType;
    private String revenueFcst;
    private String salesRdFee;
    private String rdFee;
    private Integer rdPlanCount;
    private Integer finaMode;
    private Integer employeeAmount;
    private String m_revenue;
    private String revenue;


    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(String revenueFcst) {
        this.revenueFcst = revenueFcst;
    }

    public String getSalesRdFee() {
        return salesRdFee;
    }

    public void setSalesRdFee(String salesRdFee) {
        this.salesRdFee = salesRdFee;
    }

    public String getRdFee() {
        return rdFee;
    }

    public void setRdFee(String rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getRdPlanCount() {
        return rdPlanCount;
    }

    public void setRdPlanCount(Integer rdPlanCount) {
        this.rdPlanCount = rdPlanCount;
    }

    public Integer getFinaMode() {
        return finaMode;
    }

    public void setFinaMode(Integer finaMode) {
        this.finaMode = finaMode;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public String getM_revenue() {
        return m_revenue;
    }

    public void setM_revenue(String m_revenue) {
        this.m_revenue = m_revenue;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }
}
