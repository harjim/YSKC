package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/9/25 11:45
 * description:过程文档阶段试制
 */
public class StageTrialModel implements Serializable {

    private Integer id;

    private BigDecimal rdYield;//试制量

    private BigDecimal planYield;//计划试制量

    private Date trialDate;//试制时间

    private String unit;//单位

    private Date startTime;

    private Date endTime;

    private String deptName;

    private BigDecimal workHours;

    private Integer minute;

    private String trialProduct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    public BigDecimal getPlanYield() {
        return planYield;
    }

    public void setPlanYield(BigDecimal planYield) {
        this.planYield = planYield;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTrialProduct() {
        return trialProduct;
    }

    public void setTrialProduct(String trialProduct) {
        this.trialProduct = trialProduct;
    }
}
