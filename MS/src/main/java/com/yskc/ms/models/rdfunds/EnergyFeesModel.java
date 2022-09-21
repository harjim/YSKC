package com.yskc.ms.models.rdfunds;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/4/27 11:43
 * @Description:动力归集费用model
 * @author: hsx
 */
public class EnergyFeesModel implements Serializable {

    private String ename;

    private String accNumber;

    private Date occDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;

    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Date getOccDate() {
        return occDate;
    }

    public void setOccDate(Date occDate) {
        this.occDate = occDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRdQuantity() {
        return rdQuantity;
    }

    public void setRdQuantity(BigDecimal rdQuantity) {
        this.rdQuantity = rdQuantity;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
