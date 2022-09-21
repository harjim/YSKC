package com.yskc.rs.models.tech.cost;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.utils.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.tech.cost
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 16:25
 * @Description: 导出model
 */
public class ExportTechProjectCostModel implements Serializable {

    private int num;

    private String cname;

    private String model;

    private BigDecimal fillAmount;

    private String payDates;

    private String payee;

    private String invoiceVoucher;

    private String invoiceNumber;

    private String invoiceDateStr;

    private String isBankTransferStr;

    private String bankVoucher;

    private String bankTransferDates;

    private String contractNumber;

    private String contractDateStr;

    private BigDecimal auditAmount;

    public static ExportTechProjectCostModel build(TechProjectCostModel item, int num) {
        ExportTechProjectCostModel exportModel = new ExportTechProjectCostModel();
        BeanUtil.copyProperties(item, exportModel);
        exportModel.setNum(num);
        exportModel.setIsBankTransferStr(item.getIsBankTransfer() ? "是" : "否");
        exportModel.setContractDateStr(DateUtil.format(item.getContractDate(), "yyyy-MM-dd"));
        exportModel.setInvoiceDateStr(DateUtil.format(item.getInvoiceDate(), "yyyy-MM-dd"));
        return exportModel;
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

    public String getInvoiceDateStr() {
        return invoiceDateStr;
    }

    public void setInvoiceDateStr(String invoiceDateStr) {
        this.invoiceDateStr = invoiceDateStr;
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

    public String getContractDateStr() {
        return contractDateStr;
    }

    public void setContractDateStr(String contractDateStr) {
        this.contractDateStr = contractDateStr;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
