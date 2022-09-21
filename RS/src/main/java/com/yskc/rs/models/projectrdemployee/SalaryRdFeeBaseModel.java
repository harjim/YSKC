package com.yskc.rs.models.projectrdemployee;

import com.yskc.rs.config.Constant;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-26 10:05
 * @Description: 薪资费用model
 */
public class SalaryRdFeeBaseModel {

    private Integer accountTitleId;

    private BigDecimal fee;

    private String accountName;

    private String feeDetail;

    private BigDecimal rdFunds;

    private BigDecimal rdHour;

    private BigDecimal workHours;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(String feeDetail) {
        this.feeDetail = feeDetail;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getRdRatio() {
        if (workHours != null && rdHour != null && workHours.compareTo(BigDecimal.ZERO) != 0) {
            return rdHour.divide(workHours, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
