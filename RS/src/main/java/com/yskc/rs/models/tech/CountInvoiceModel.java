package com.yskc.rs.models.tech;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/3/23 16:32
 * @Description:统计发票信息
 */
public class CountInvoiceModel implements Serializable {

    private Integer id;

    private BigDecimal taxAmount;

    private BigDecimal amount;

    private String type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
