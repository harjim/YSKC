package com.yskc.rs.models.company;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/15 11:51
 * @Description:首页研发费用统计model
 */
public class CountRdFundModel implements Serializable {

    private Integer rdType;

    private BigDecimal rdFund;//归集费用

    private BigDecimal totalRdFund;//总归集费用

    private Date month;//年度研发费使用月份

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public CountRdFundModel() {
    }

    public CountRdFundModel(Integer rdType, BigDecimal rdFund, BigDecimal totalRdFund) {
        this.rdType = rdType;
        this.rdFund = rdFund;
        this.totalRdFund = totalRdFund;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }
    public BigDecimal getRdFund() {
        return rdFund;
    }

    public void setRdFund(BigDecimal rdFund) {
        this.rdFund = rdFund;
    }

    public BigDecimal getTotalRdFund() {
        return totalRdFund;
    }

    public void setTotalRdFund(BigDecimal totalRdFund) {
        this.totalRdFund = totalRdFund;
    }
}
