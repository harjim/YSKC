package com.yskc.ms.models.projectAudit;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/11/24 11:13
 * description:研发花名册列表
 */
public class RdEmployeeListModel implements Serializable {

    private Integer id;

    private Integer year;

    private String enumber;

    private String ename;

    private Integer rdDeptId;

    private Integer etype;

    private String deptName;

    private String specialities;//专业

    private String hasBind;

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getHasBind() {
        return hasBind;
    }

    public void setHasBind(String hasBind) {
        this.hasBind = hasBind;
    }
}
