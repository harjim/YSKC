package com.yskc.rs.models.trialprod;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/5/30 11:03
 * description:
 */
public class TrialProdSumarryModel {

    private Integer id;
    /**
     * 月份
     */
    private String month;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trialDate;

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 时长(分钟)
     */
    private Integer workHours;
    /**
     * 时长(小时)
     */
    private BigDecimal hours;
    /**
     * 单位
     */
    private String unit;
    /**
     * 地点
     */

    private String place;

    private Integer actualPO;

    private Integer planPO;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getActualPO() {
        return actualPO;
    }

    public void setActualPO(Integer actualPO) {
        this.actualPO = actualPO;
    }

    public Integer getPlanPO() {
        return planPO;
    }

    public void setPlanPO(Integer planPO) {
        this.planPO = planPO;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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


}
