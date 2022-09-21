package com.yskc.rs.models.tech.cost;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.tech.cost
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 08:34
 * @Description: 项目支出model
 */
public class TechProjectCostModel implements Serializable {

    private Integer id;

    @NotNull(message = "无法获取关联项目")
    private Integer projectId;

    @NotNull(message = "支出类别不能为空")
    private Integer ctype;

    @NotBlank(message = "支出内容不能为空")
    @Size(max = 200, message = "支出内容不能超过200个字")
    private String cname;

    @NotBlank(message = "规格 型号不能为空")
    @Size(max = 30, message = "规格 型号不能超过30个字")
    private String model;

    @NotNull(message = "企业填报金额不能为空")
    private BigDecimal fillAmount;

    @NotBlank(message = "支出时间不能为空")
    @Size(max = 200, message = "支出时间不能超过200个字")
    private String payDates;

    @NotBlank(message = "收款单位不能为空")
    @Size(max = 50, message = "收款单位不能超过50个字")
    private String payee;

    @NotBlank(message = "发票记账凭证字号不能为空")
    @Size(max = 200, message = "发票记账凭证字号不能超过200个字")
    private String invoiceVoucher;

    @NotBlank(message = "发票号码不能为空")
    @Size(max = 200, message = "发票号码不能超过200个字")
    private String invoiceNumber;

    @NotNull(message = "发票日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceDate;

    private Boolean isBankTransfer;

    @NotBlank(message = "银行转账记账凭证字号不能为空")
    @Size(max = 200, message = "银行转账记账凭证字号不能超过200个字")
    private String bankVoucher;
    @NotBlank(message = "银行转账时间不能为空")
    @Size(max = 200, message = "银行转账时间不能超过200个字")
    private String bankTransferDates;

    @NotBlank(message = "合同/协议编号不能为空")
    @Size(max = 40, message = "合同/协议编号不能超过40个字")
    private String contractNumber;

    @NotNull(message = "合同/协议日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractDate;

    @NotNull(message = "审计确定金额不能为空")
    private BigDecimal auditAmount;

    @NotNull(message = "数量不能为空")
    private Integer quantity;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setFillAmount(BigDecimal fillAmount) {
        this.fillAmount = fillAmount;
    }

    public BigDecimal getFillAmount() {
        return fillAmount;
    }

    public void setPayDates(String payDates) {
        this.payDates = payDates;
    }

    public String getPayDates() {
        return payDates;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayee() {
        return payee;
    }

    public void setInvoiceVoucher(String invoiceVoucher) {
        this.invoiceVoucher = invoiceVoucher;
    }

    public String getInvoiceVoucher() {
        return invoiceVoucher;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public Boolean getBankTransfer() {
        return isBankTransfer;
    }

    public void setBankTransfer(Boolean bankTransfer) {
        isBankTransfer = bankTransfer;
    }

    public Boolean getIsBankTransfer() {
        return isBankTransfer;
    }

    public void setIsBankTransfer(Boolean bankTransfer) {
        isBankTransfer = bankTransfer;
    }


    public void setBankVoucher(String bankVoucher) {
        this.bankVoucher = bankVoucher;
    }

    public String getBankVoucher() {
        return bankVoucher;
    }

    public void setBankTransferDates(String bankTransferDates) {
        this.bankTransferDates = bankTransferDates;
    }

    public String getBankTransferDates() {
        return bankTransferDates;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
