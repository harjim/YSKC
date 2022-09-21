package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rs
 * @description: 投资清单付款（原银行水单）
 * @author: wjy
 * @create: 2022-09-19 15:19
 **/
@TableName("t_payment")
public class PaymentEntity extends BaseEntity{
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer investmentId;
    private Integer seq;
    private String voucherNo;
    private Date voucherDate;
    private Integer payType;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Date acceptDate;
    private BigDecimal amount;
    private Date payDate;
    private String payee;
    private Integer payStage;
    private BigDecimal payRate;
    private String voucherPath;
    private String bankReceiptPath;
    private BigDecimal amountTax;
    private BigDecimal taxRate;

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Integer getPayStage() {
        return payStage;
    }

    public void setPayStage(Integer payStage) {
        this.payStage = payStage;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
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
