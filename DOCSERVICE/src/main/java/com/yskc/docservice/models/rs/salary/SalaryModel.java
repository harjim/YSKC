package com.yskc.docservice.models.rs.salary;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.docservice.entity.rs.SalaryEntity;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class SalaryModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String enumber;
    /**
     *
     */
    private String ename;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;
    /**
     *
     */
    private BigDecimal workDays;
    private BigDecimal pay;
    private BigDecimal insuranceFund;
    private String deptName;
    private String fullAccountName;
    private Integer creatorId;
    private Date createTime;
    @Size(max = 200, message = "备注不能超过200个字")
    private String remark;

    private Integer dayHours;
    private List<SalaryEntity> salarylist;
    private Integer accountTitleId;

    private BigDecimal workHours;

    private String payDetail;

    private String insuranceDetail;

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public Integer getDayHours() {
        return dayHours;
    }

    public void setDayHours(Integer dayHours) {
        this.dayHours = dayHours;
    }

    public List<SalaryEntity> getSalarylist() {
        return salarylist;
    }

    public void setSalarylist(List<SalaryEntity> salarylist) {
        this.salarylist = salarylist;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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
}
