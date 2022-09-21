package com.yskc.rs.models.projectattendance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-16 14:22
 * @Description: 人员研发考勤小时info
 */
public class BatchProjectHourAttendance {
    private Integer id;
    private String enumber;
    private BigDecimal remainHours;
    private BigDecimal baseHours;
    private Date workDate;
    private BigDecimal workHour;
    private Integer index;
    private Boolean owner;
    private Integer projectId;

    public BatchProjectHourAttendance() {
    }

    public BatchProjectHourAttendance(Integer id,String enumber, Date workDate, BigDecimal workHour, Integer index) {
        this.id = id;
        this.enumber = enumber;
        this.workDate = workDate;
        this.workHour = workHour;
        this.index = index;
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

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public BigDecimal getBaseHours() {
        return baseHours;
    }

    public void setBaseHours(BigDecimal baseHours) {
        this.baseHours = baseHours;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
