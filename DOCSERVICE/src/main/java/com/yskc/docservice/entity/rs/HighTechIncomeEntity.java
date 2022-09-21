package com.yskc.docservice.entity.rs;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.entity.rs.tech.BaseEntity;
import com.yskc.docservice.models.rs.HighTechIncomeModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:18
 * @Description: 高新产品收入
 */
@TableName("highTech_income")
public class HighTechIncomeEntity extends BaseEntity {

    @TableId
    private Integer id;

    private Integer companyId;
    private Integer highTechId;
    //产品名称
    private String productName;
    //记账日期
    private Date bookDate;
    // 凭证号
    private String voucherNo;
    // 数量
    private BigDecimal quantity;
    // 单价
    private BigDecimal unitPrice;
    // 高新收入
    private BigDecimal income;
    // 客户名称
    private String client;

    public static HighTechIncomeEntity build(HighTechIncomeModel income, Integer userId, Integer msUserId, Integer companyId, Date date) {
        HighTechIncomeEntity entity = new HighTechIncomeEntity();
        BeanUtil.copyProperties(income, entity);
        if (null == income.getId()) {
            entity.setCompanyId(companyId);
            entity.create(userId, msUserId, date);
        } else {
            entity.update(userId, msUserId, date);
        }
        return entity;
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
}
