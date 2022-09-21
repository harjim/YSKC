package com.yskc.rs.models.projectattendance;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-16 14:20
 * @Description: 人员研发考勤小时
 */
public class ProjectHourAttendance {
    private Integer id;
    private String enumber;
    private String ename;
    private String deptName;
    private Integer etype;
    private BigDecimal rdHour;
    private BigDecimal attendanceHour;
    private BigDecimal workHours;
    private Map<Integer, BatchProjectHourAttendance> info;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public Map<Integer, BatchProjectHourAttendance> getInfo() {
        return info;
    }

    public void setInfo(Map<Integer, BatchProjectHourAttendance> info) {
        this.info = info;
    }

    public BigDecimal getAttendanceHour() {
        return attendanceHour;
    }

    public void setAttendanceHour(BigDecimal attendanceHour) {
        this.attendanceHour = attendanceHour;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }
}
