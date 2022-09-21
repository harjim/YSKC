package com.yskc.ms.models.projectAudit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: ms
 * @description: 据项目月份返回人员数据
 * @author: cyj
 * @create: 2022-04-27 10:45
 **/
public class RdFeeEmployeeModel extends PageParams implements Serializable {
    private String enumber;
    private Integer etype;
    private String ename;
    private String deptName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
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
}
