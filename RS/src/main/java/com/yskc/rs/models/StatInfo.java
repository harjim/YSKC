package com.yskc.rs.models;

import java.math.BigDecimal;

public class StatInfo {
    private Integer year;
    private Integer count;
    private Integer selfRD;
    private BigDecimal amount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSelfRD() {
        return selfRD;
    }

    public void setSelfRD(Integer selfRD) {
        this.selfRD = selfRD;
    }
}
