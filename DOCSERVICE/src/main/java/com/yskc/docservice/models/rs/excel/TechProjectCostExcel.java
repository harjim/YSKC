package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 13:56
 * @Description: 项目支出excel
 */
public class TechProjectCostExcel implements Serializable {

    @Excel(name = "支出类别")
    private String ctypeName;

    @Excel(name = "支出内容")
    private String cname;

    @Excel(name = "规格 型号")
    private String model;

    @Excel(name = "企业填报金额")
    private BigDecimal fillAmount;

    @Excel(name = "支出时间")
    private String payDates;

    @Excel(name = "发票记账凭证字号")
    private String payee;

    @Excel(name = "收款单位")
    private String invoiceVoucher;

    @Excel(name = "发票号码")
    private String invoiceNumber;

    @Excel(name = "发票日期")
    private Date invoiceDate;

    @Excel(name = "是否银行转账")
    private String isBankTransferStr;

    @Excel(name = "银行转账时间")
    private String bankVoucher;

    @Excel(name = "银行转账记账凭证字号")
    private String bankTransferDates;

    @Excel(name = "合同/协议编号")
    private String contractNumber;

    @Excel(name = "合同/协议日期")
    private Date contractDate;

    @Excel(name = "审计确定金额")
    private BigDecimal auditAmount;

    @Excel(name = "数量")
    private Integer quantity;

    public String getCtypeName() {
        return ctypeName;
    }

    public void setCtypeName(String ctypeName) {
        this.ctypeName = ctypeName;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getFillAmount() {
        return fillAmount;
    }

    public void setFillAmount(BigDecimal fillAmount) {
        this.fillAmount = fillAmount;
    }

    public String getPayDates() {
        return payDates;
    }

    public void setPayDates(String payDates) {
        this.payDates = payDates;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getInvoiceVoucher() {
        return invoiceVoucher;
    }

    public void setInvoiceVoucher(String invoiceVoucher) {
        this.invoiceVoucher = invoiceVoucher;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getIsBankTransferStr() {
        return isBankTransferStr;
    }

    public void setIsBankTransferStr(String isBankTransferStr) {
        this.isBankTransferStr = isBankTransferStr;
    }

    public String getBankVoucher() {
        return bankVoucher;
    }

    public void setBankVoucher(String bankVoucher) {
        this.bankVoucher = bankVoucher;
    }

    public String getBankTransferDates() {
        return bankTransferDates;
    }

    public void setBankTransferDates(String bankTransferDates) {
        this.bankTransferDates = bankTransferDates;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
