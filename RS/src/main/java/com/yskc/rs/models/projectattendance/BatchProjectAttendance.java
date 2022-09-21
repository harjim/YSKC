package com.yskc.rs.models.projectattendance;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 批量操作研发考勤
 *
 * @author huronghua
 */
public class BatchProjectAttendance implements Serializable {
    private Integer id;
    private Integer usedId;
    private String timeRange;
    private String enumber;
    private BigDecimal remainHours;
    private BigDecimal baseHours;
   private BigDecimal usedWorkHour;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date offTime1;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date onTime1;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date offTime2;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date onTime2;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date offTime3;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date onTime3;
    private BigDecimal workHour;
    private Date workDate;
    private Integer index;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsedId() {
        return usedId;
    }

    public void setUsedId(Integer usedId) {
        this.usedId = usedId;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public BigDecimal getUsedWorkHour() {
        return usedWorkHour;
    }

    public void setUsedWorkHour(BigDecimal usedWorkHour) {
        this.usedWorkHour = usedWorkHour;
    }

    public Date getOffTime1() {
        return offTime1;
    }

    public void setOffTime1(Date offTime1) {
        this.offTime1 = offTime1;
    }

    public Date getOnTime1() {
        return onTime1;
    }

    public void setOnTime1(Date onTime1) {
        this.onTime1 = onTime1;
    }

    public Date getOffTime2() {
        return offTime2;
    }

    public void setOffTime2(Date offTime2) {
        this.offTime2 = offTime2;
    }

    public Date getOnTime2() {
        return onTime2;
    }

    public void setOnTime2(Date onTime2) {
        this.onTime2 = onTime2;
    }

    public Date getOffTime3() {
        return offTime3;
    }

    public void setOffTime3(Date offTime3) {
        this.offTime3 = offTime3;
    }

    public Date getOnTime3() {
        return onTime3;
    }

    public void setOnTime3(Date onTime3) {
        this.onTime3 = onTime3;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BigDecimal getBaseHours() {
        return baseHours;
    }

    public void setBaseHours(BigDecimal baseHours) {
        this.baseHours = baseHours;
    }
}
