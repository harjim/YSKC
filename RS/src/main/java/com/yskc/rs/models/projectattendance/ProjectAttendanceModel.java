package com.yskc.rs.models.projectattendance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 研发人员报表模型
 *
 * @author huronghua
 */
public class ProjectAttendanceModel implements Serializable {
    private Integer id;
    private Integer projectId;
    private String enumber;
    private String ename;
    private Integer deptId;
    private BigDecimal rdHour;
    private Map<Integer, BatchProjectAttendance> info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Map<Integer, BatchProjectAttendance> getInfo() {
        return info;
    }

    public void setInfo(Map<Integer, BatchProjectAttendance> info) {
        this.info = info;
    }
}
