package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.docservice.enums.EmployeeTypeEnum;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工薪资
 */
public class SalaryExcel implements Serializable {
    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;
    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;
    @Excel(name = "所属部门", order = 2, fieldName = "deptName")
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "工资月份", order = 3, fieldName = "month", dateFormat = "yyyy-MM")
    private Date month;
    @Excel(name = "工作天数", order = 4, fieldName = "workDays")
    private BigDecimal workDays;
    @Excel(name = "应发工资", order = 5, fieldName = "pay")
    private BigDecimal pay;
    @Excel(name = "备注", order = 18, fieldName = "remark")
    private String remark;
    @Excel(name = "科目", order = 17, fieldName = "fullAccountName")
    private String fullAccountName;
    private Integer dayHours;
    private Integer etype;
    private String typeValue;
    private String rdDeptName;
    private BigDecimal workHours;

    private String payDetail;

    private BigDecimal insuranceFund;

    private String insuranceDetail;

    public Integer getDayHours() {
        return dayHours;
    }

    public void setDayHours(Integer dayHours) {
        this.dayHours = dayHours;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getTypeValue() {
        if (!StringUtils.hasLength(typeValue)) {
            EmployeeTypeEnum typeEnum = EmployeeTypeEnum.getEmployeeTypeEnum(etype);
            return typeEnum.getType();
        }
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEname() {
        return ename;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }
}
