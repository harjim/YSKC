package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 11:25
 * @Description:发票model
 */
public class InvoiceModel implements Serializable {
    private String ename;
    private String emodal;
    private BigDecimal quantity;
    private Integer id;//发票详情id
    private Integer seq;
    private String invoiceNo;
    private BigDecimal taxAmount;
    private BigDecimal excludeTaxAmount;
    private String invoiceFrom;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date voucherDate;
    private String invoicePath;
    private String voucherNo;
    private String voucherPath;
    private Integer investmentId;
    private Integer invoiceId;//发票id

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public BigDecimal getExcludeTaxAmount() {
        return excludeTaxAmount;
    }

    public void setExcludeTaxAmount(BigDecimal excludeTaxAmount) {
        this.excludeTaxAmount = excludeTaxAmount;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getInvoiceFrom() {
        return invoiceFrom;
    }

    public void setInvoiceFrom(String invoiceFrom) {
        this.invoiceFrom = invoiceFrom;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherPath() {
        return voucherPath;
    }

    public void setVoucherPath(String voucherPath) {
        this.voucherPath = voucherPath;
    }
}
