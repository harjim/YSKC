package com.yskc.ms.models.checkInst;

import java.math.BigDecimal;

/**
 * @program: ms
 * @description: 查新收费标准
 * @author: cyj
 * @create: 2022-08-09 10:12
 **/
public class CheckPriceModel {
    private Integer id;
    private Integer checkInstId;
    private Integer days;
    private BigDecimal amount;
    private Integer checkType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckInstId() {
        return checkInstId;
    }

    public void setCheckInstId(Integer checkInstId) {
        this.checkInstId = checkInstId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }
}
