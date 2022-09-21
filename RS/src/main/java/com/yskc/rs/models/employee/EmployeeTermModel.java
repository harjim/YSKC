package com.yskc.rs.models.employee;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: wangxing
 * @CreateTime: 2019-09-06 15:44
 * @Description: 多条件查询model
 */
public class EmployeeTermModel extends PageParams {
    private String ename;
    private String[] etypes;
    private String enumber;
    private String position;
    private String idNumber;
    private Integer eduLevel;
    private Date edate;
    private Date beginEdate;
    private Date endEdate;
    private String title;
    private Integer year;
    private String specialities;
    @Deprecated
    private String rdDeptPath;
    @Deprecated
    private String deptPath;
    private String deptName;
    private String remark;

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String[] getEtypes() {
        return etypes;
    }

    public void setEtypes(String[] etypes) {
        this.etypes = etypes;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Date getBeginEdate() {
        return beginEdate;
    }

    public void setBeginEdate(Date beginEdate) {
        this.beginEdate = beginEdate;
    }

    public Date getEndEdate() {
        return endEdate;
    }

    public void setEndEdate(Date endEdate) {
        this.endEdate = endEdate;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
