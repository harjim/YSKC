package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: rs
 * @description: 科技管理人员导入
 * @author: cyj
 * @create: 2022-08-09 08:15
 **/
public class StEmployeeExcel implements Serializable {
    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;
    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;
    @Excel(name = "部门", order = 2, fieldName = "deptName")
    private String deptName;
    @Excel(name = "职位", order = 3, fieldName = "position")
    private String position;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "出生日期", order = 4, fieldName = "birthday")
    private Date birthday;
    @Excel(name = "职称", order = 5, fieldName = "title")
    private String title;
    @Excel(name = "身份证号", order = 6, fieldName = "idNumber")
    private String idNumber;
    @Excel(name = "学历", order = 7, fieldName = "eduLevel")
    private String eduLevel;
    @Excel(name = "专业", order = 8, fieldName = "specialities")
    private String specialities;
    @Excel(name = "入职日期", order = 9, fieldName = "edate", dateFormat = "yyyy-MM-dd")
    private Date edate;
    @Excel(name = "离职日期", order = 10, fieldName = "leaveDate", dateFormat = "yyyy-MM-dd")
    private Date leaveDate;
    private String gender;
    private int genderType;

    public int getGenderType() {
        return genderType;
    }

    public void setGenderType(int genderType) {
        this.genderType = genderType;
    }

    public String getGender() {
        if (StringUtils.isEmpty(gender)) {
            if (genderType == 1) {
                gender = "女";
            }
            if (genderType == 2) {
                gender = "男";
            }
            if (genderType == 0 || genderType == -1) {
                gender = "无";
            }
        }
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }
}

