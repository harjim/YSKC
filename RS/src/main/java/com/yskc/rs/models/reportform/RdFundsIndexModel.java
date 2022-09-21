package com.yskc.rs.models.reportform;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: rs
 * @description: 研发费用归集指标model
 * @author: cyj
 * @create: 2022-01-19 16:25
 **/
public class RdFundsIndexModel implements Serializable {
    private Double amount;
    private Double m_amount;
    private Double material;
    private Double m_material;
    //本年累计可加计扣除研发费
    private Double estimatedCost;
    //研发费总额/年累计营收入
    private String totalAmountIRevunue;
    //可加计扣除研发费/总营收
    private String axAmountIRevunue;
    //研发费同期比
    private String amountIncrease;
    //总研发费与上年比
    private String totalAmountIncrease;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getM_amount() {
        return m_amount;
    }

    public void setM_amount(Double m_amount) {
        this.m_amount = m_amount;
    }

    public Double getMaterial() {
        return material;
    }

    public void setMaterial(Double material) {
        this.material = material;
    }

    public Double getM_material() {
        return m_material;
    }

    public void setM_material(Double m_material) {
        this.m_material = m_material;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getTotalAmountIRevunue() {
        return totalAmountIRevunue;
    }

    public void setTotalAmountIRevunue(String totalAmountIRevunue) {
        this.totalAmountIRevunue = totalAmountIRevunue;
    }

    public String getAxAmountIRevunue() {
        return axAmountIRevunue;
    }

    public void setAxAmountIRevunue(String axAmountIRevunue) {
        this.axAmountIRevunue = axAmountIRevunue;
    }

    public String getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(String amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public String getTotalAmountIncrease() {
        return totalAmountIncrease;
    }

    public void setTotalAmountIncrease(String totalAmountIncrease) {
        this.totalAmountIncrease = totalAmountIncrease;
    }
}
