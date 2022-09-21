package com.yskc.rs.models.company;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/15 13:41
 * @Description:首页统计归集占预算比model
 */
public class CountBudgetModel implements Serializable {

    private Date month;//月份

    private BigDecimal rdFunds;//月归集费用

    private BigDecimal totalRdFunds;//累计归集费用

    private BigDecimal allocations;//可分配额度 预算-已归集/预算

    private BigDecimal budget;//年总预算

    private BigDecimal percent;//占比

    private String monthStr;//月份字符串

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public CountBudgetModel() {
    }

    public CountBudgetModel(String month, BigDecimal rdFunds, BigDecimal totalRdFunds, BigDecimal allocations, BigDecimal budget, BigDecimal percent) {
        this.monthStr = month;
        this.rdFunds = rdFunds;
        this.totalRdFunds = totalRdFunds;
        this.allocations = allocations;
        this.budget = budget;
        this.percent = percent;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public BigDecimal getTotalRdFunds() {
        return totalRdFunds;
    }

    public void setTotalRdFunds(BigDecimal totalRdFunds) {
        this.totalRdFunds = totalRdFunds;
    }

    public BigDecimal getAllocations() {
        return allocations;
    }

    public void setAllocations(BigDecimal allocations) {
        this.allocations = allocations;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
