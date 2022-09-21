package com.yskc.rs.models.projectrdemployee;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-19 17:53
 * @Description: 工资核算model
 */
public class SalaryAccountingModel {

    private String enumber;
    private String ename;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insuranceFund;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdPay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdInsuranceFund;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;
    private String deptName;
    private Integer etype;
    private String position;
    private String payDetail;
    private String insuranceDetail;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal planTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal attendanceHour;

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
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

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public BigDecimal getAttendanceHour() {
        return attendanceHour;
    }

    public void setAttendanceHour(BigDecimal attendanceHour) {
        this.attendanceHour = attendanceHour;
    }
}
