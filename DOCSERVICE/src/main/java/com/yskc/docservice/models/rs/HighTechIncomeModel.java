package com.yskc.docservice.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highTech
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-28 16:01
 * @Description: 高品收入
 */
public class HighTechIncomeModel {

    private Integer id;

    private Integer highTechId;
    /**
     * 产品名称
     */
    @NotEmpty(message = "产品名称不能为空")
    private String productName;
    /**
     * 记账日期
     */
    @NotNull(message = "记账日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date bookDate;
    /**
     * 凭证号
     */
    @NotEmpty(message = "凭证号不能为空")
    private String voucherNo;
    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;
    /**
     * 单价
     */
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;
    /**
     * 高新收入
     */
    @NotNull(message = "高新收入不能为空")
    private BigDecimal income;
    /**
     * 客户名称
     */
    private String client;

    private String hcode;

    private String hname;

    private BigDecimal totalIncome;//总收入

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }
}
