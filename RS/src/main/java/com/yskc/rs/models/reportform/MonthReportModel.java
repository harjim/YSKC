package com.yskc.rs.models.reportform;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 研发月报表model
 * @author: cyj
 * @create: 2022-01-21 13:46
 **/
public class MonthReportModel implements Serializable {
    private Integer month;
    private String revenue;
    private String m_revenue;
    private String amount;
    private String m_amount;
    private String material;
    private String m_material;
    private String exAmount;
    private String amountIRevenue;
    private String exAmountIRevenue;
    private String amountIncrease;
    private String mAmountIcnrease;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getM_amount() {
        return m_amount;
    }

    public void setM_amount(String m_amount) {
        this.m_amount = m_amount;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getM_revenue() {
        return m_revenue;
    }

    public void setM_revenue(String m_revenue) {
        this.m_revenue = m_revenue;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getM_material() {
        return m_material;
    }

    public void setM_material(String m_material) {
        this.m_material = m_material;
    }

    public String getExAmount() {
        return exAmount;
    }

    public void setExAmount(String exAmount) {
        this.exAmount = exAmount;
    }

    public String getAmountIRevenue() {
        return amountIRevenue;
    }

    public void setAmountIRevenue(String amountIRevenue) {
        this.amountIRevenue = amountIRevenue;
    }

    public String getExAmountIRevenue() {
        return exAmountIRevenue;
    }

    public void setExAmountIRevenue(String exAmountIRevenue) {
        this.exAmountIRevenue = exAmountIRevenue;
    }

    public String getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(String amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public String getmAmountIcnrease() {
        return mAmountIcnrease;
    }

    public void setmAmountIcnrease(String mAmountIcnrease) {
        this.mAmountIcnrease = mAmountIcnrease;
    }
}
