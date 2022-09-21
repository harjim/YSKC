package com.yskc.rs.models.salary;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.salary
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-12 10:35
 * @Description: 薪资信息model
 */
public class SalaryInfoModel {
    private String enumber;
    private BigDecimal pay;
    private BigDecimal insuranceFund;
    private BigDecimal workHours;
    private String payDetail;
    private String insuranceDetail;
    private BigDecimal remainHours;

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

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

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public BigDecimal getRemainHours() {
        return remainHours == null ? workHours : remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }
}
