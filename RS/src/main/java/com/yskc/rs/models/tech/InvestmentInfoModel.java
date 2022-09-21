package com.yskc.rs.models.tech;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 10:00
 * @Description:
 */
public class InvestmentInfoModel implements Serializable {

    private Integer investmentId;

    private BigDecimal totalQuantity;

    private BigDecimal totalAmount;

    private BigDecimal totalBank;

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalBank() {
        return totalBank;
    }

    public void setTotalBank(BigDecimal totalBank) {
        this.totalBank = totalBank;
    }
}
