package com.yskc.rs.models.projectattendance;

import com.yskc.rs.enums.EmployeeTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2022-07-15 09:24
 * @Description: 导出model
 */
public class ExportProjectAttendanceModel {

    private String enumber;
    private String ename;
    private BigDecimal rdHour;
    private String deptName;
    private String rdTitle;
    private Integer projectId;
    private BigDecimal workHours;
    private Integer etype;
    private List<BigDecimal> hours;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public List<BigDecimal> getHours() {
        return hours;
    }

    public void setHours(List<BigDecimal> hours) {
        this.hours = hours;
    }

    public String getEtypeName() {
        return EmployeeTypeEnum.getEmployeeTypeEnum(etype).getType();
    }

}
