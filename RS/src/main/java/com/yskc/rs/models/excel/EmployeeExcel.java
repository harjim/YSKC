package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.rs.enums.EduLevelEnum;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工花名册
 */
public class EmployeeExcel implements Serializable {
    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;
    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;
    @Excel(name = "所属部门", order = 2, fieldName = "deptName")
    private String deptName;
    @Excel(name = "职位", order = 3, fieldName = "position")
    private String position;
    @Excel(name = "身份证号", order = 4, fieldName = "idNumber")
    private String idNumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "入职日期", order = 5, fieldName = "edate", dateFormat = "yyyy-MM-dd")
    private Date edate;
    @Excel(name = "学历", order = 6, fieldName = "eduLevel")
    private String eduLevel;
    @Excel(name = "职称", order = 9, fieldName = "title")
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "离职日期", order = 7, fieldName = "edate", dateFormat = "yyyy-MM-dd")
    private Date leaveDate;
    @Excel(name = "备注", order = 12, fieldName = "remark")
    private String remark;
    @Excel(name = "专业", order = 10, fieldName = "specialities")
    private String specialities;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "出生日期", order = 11, fieldName = "birthday")
    private Date birthday;
    @Excel(name = "性别", order = 12, fieldName = "gender")
    private String gender;
    private Integer genderType;
    private String rdDeptName;
    private String rds;
    private Integer type;
    private String lackMonth;
    private String basePosition;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getGenderType() {
        return genderType;
    }

    public void setGenderType(Integer genderType) {
        this.genderType = genderType;
    }

    private int level;

    public String getGender() {
        if (StringUtils.isEmpty(gender) && genderType != null) {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getEduLevel() {
        if (StringUtils.isEmpty(eduLevel)) {
            EduLevelEnum levelEnum = EduLevelEnum.getEduLevelEnum(level);
            return levelEnum.getEdu();
        }
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLackMonth() {
        return lackMonth;
    }

    public void setLackMonth(String lackMonth) {
        this.lackMonth = lackMonth;
    }

    public String getBasePosition() {
        return basePosition;
    }

    public void setBasePosition(String basePosition) {
        this.basePosition = basePosition;
    }
}
