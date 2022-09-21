package com.yskc.rs.models.tech;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 15:24
 * @Description:
 */
public class InvoiceDetailModel implements Serializable {

    private Integer id;
    private Integer invoiceId;
    private String ename;
    private String emodal;
    private BigDecimal quantity;
    private BigDecimal excludeTaxPrice;
    private BigDecimal taxRate;
    private BigDecimal taxPrice;
    private BigDecimal excludeTaxAmount;
    private BigDecimal taxAmount;
    private Integer investmentId;

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getExcludeTaxPrice() {
        return excludeTaxPrice;
    }

    public void setExcludeTaxPrice(BigDecimal excludeTaxPrice) {
        this.excludeTaxPrice = excludeTaxPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getExcludeTaxAmount() {
        return excludeTaxAmount;
    }

    public void setExcludeTaxAmount(BigDecimal excludeTaxAmount) {
        this.excludeTaxAmount = excludeTaxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
}
