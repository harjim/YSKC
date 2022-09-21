package com.yskc.rs.models.projectattendance;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-16 17:16
 * @Description: 导出研发考勤时间
 */
public class TimeAttendanceModel {
    private Integer id;
    private String enumber;
    private String ename;
    private Integer etype;
    private String etypeName;
    private String deptName;
    private BigDecimal workHour;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workDate;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date onTime1;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date onTime2;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date onTime3;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date offTime1;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date offTime2;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date offTime3;
    private Integer projectId;

    public String getEtypeName() {
        return etypeName;
    }

    public void setEtypeName(String etypeName) {
        this.etypeName = etypeName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
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

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
