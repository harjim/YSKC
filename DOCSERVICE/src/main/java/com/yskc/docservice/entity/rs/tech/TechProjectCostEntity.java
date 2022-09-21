package com.yskc.docservice.entity.rs.tech;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-24 17:32
 * @Description: 项目支出
 */
@TableName("t_projectCost")
public class TechProjectCostEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5557984531860852098L;
    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Integer ctype;
    /**
     *
     */
    private String cname;
    /**
     *
     */
    private String model;
    /**
     *
     */
    private BigDecimal fillAmount;
    /**
     *
     */
    private String payDates;
    /**
     *
     */
    private String payee;
    /**
     *
     */
    private String invoiceVoucher;
    /**
     *
     */
    private String invoiceNumber;
    /**
     *
     */
    private Date invoiceDate;
    /**
     *
     */
    private Boolean isBankTransfer;
    /**
     *
     */
    private String bankVoucher;
    /**
     *
     */
    private String bankTransferDates;
    /**
     *
     */
    private String contractNumber;
    /**
     *
     */
    private Date contractDate;
    /**
     *
     */
    private BigDecimal auditAmount;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;

    private Integer quantity;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
