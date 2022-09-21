package com.yskc.ms.models.rs.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-23 16:45
 * @Description: 项目研发工时记录
 */
public class ProjectAttendanceModel {

    private Integer id;
    private String enumber;
    private Date workDate;
    private Date offTime1;
    private Date offTime2;
    private Date offTime3;
    private Date onTime1;
    private Date onTime2;
    private Date onTime3;
    private BigDecimal workHour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getOffTime1() {
        return offTime1;
    }

    public void setOffTime1(Date offTime1) {
        this.offTime1 = offTime1;
    }

    public Date getOffTime2() {
        return offTime2;
    }

    public void setOffTime2(Date offTime2) {
        this.offTime2 = offTime2;
    }

    public Date getOffTime3() {
        return offTime3;
    }

    public void setOffTime3(Date offTime3) {
        this.offTime3 = offTime3;
    }

    public Date getOnTime1() {
        return onTime1;
    }

    public void setOnTime1(Date onTime1) {
        this.onTime1 = onTime1;
    }

    public Date getOnTime2() {
        return onTime2;
    }

    public void setOnTime2(Date onTime2) {
        this.onTime2 = onTime2;
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
}
