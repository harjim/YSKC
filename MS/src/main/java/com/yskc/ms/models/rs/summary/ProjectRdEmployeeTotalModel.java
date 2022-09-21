package com.yskc.ms.models.rs.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:48
 * @Description: 项目人员费用总计
 */
public class ProjectRdEmployeeTotalModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insuranceFund;

    private Date month;

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

}
