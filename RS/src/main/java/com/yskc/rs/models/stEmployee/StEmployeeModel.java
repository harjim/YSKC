package com.yskc.rs.models.stEmployee;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @program: rs
 * @description: 科技管理人员
 * @author: cyj
 * @create: 2022-08-08 10:44
 **/
public class StEmployeeModel {

    private Integer id;
    private Integer companyId;
    private Integer year;
    private String enumber;
    private String ename;
    private String position;
    private String idNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date edate;
    private Integer eduLevel;
    @Size(max = 300, message = "备注不能超过300个字")
    private String remark;
    private String deptName;
    private String edLevel;
    private String aliasType;
    private String oldEnumber;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;
    private String specialities;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer gender;
    private List<String> enumbers;

    public List<String> getEnumbers() {
        return enumbers;
    }

    public void setEnumbers(List<String> enumbers) {
        this.enumbers = enumbers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEdLevel() {
        return edLevel;
    }

    public void setEdLevel(String edLevel) {
        this.edLevel = edLevel;
    }

    public String getAliasType() {
        return aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
    }

    public String getOldEnumber() {
        return oldEnumber;
    }

    public void setOldEnumber(String oldEnumber) {
        this.oldEnumber = oldEnumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
