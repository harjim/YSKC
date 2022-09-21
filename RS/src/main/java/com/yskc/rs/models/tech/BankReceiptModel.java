package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/22 13:53
 * @Description:银行水单
 */
public class BankReceiptModel implements Serializable {

    private Integer id;
    private Boolean transfer;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date transferDate;
    private BigDecimal amount;
    private String payee;
    private String voucherNo;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDate;
    private String voucherPath;
    private String bankReceiptPath;
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

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getVoucherPath() {
        return voucherPath;
    }

    public void setVoucherPath(String voucherPath) {
        this.voucherPath = voucherPath;
    }

    public String getBankReceiptPath() {
        return bankReceiptPath;
    }

    public void setBankReceiptPath(String bankReceiptPath) {
        this.bankReceiptPath = bankReceiptPath;
    }
}
