package com.yskc.docservice.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.docservice.entity.rs.EmployeeEntity;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: wangxing
 * @CreateTime: 2019-11-18 14:49
 * @Description: RdEmployeeModel
 */
public class RdEmployeeModel {
    private Integer id;
    private Integer year;
    private Integer companyId;
    private String enumber;
    private Integer etype;
    private Integer rdDeptId;
    private String ename;
    private String deptName;
    private String position;
    private String basePosition;
    private String idNumber;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date edate;
    private Integer eduLevel;
    private String remark;

    private String edLevel;
    private String aliasType;
    private String specialities;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date leaveDate;
    private List<EmployeeEntity> entityList;
    private List<Integer> ids;
    /**
     * 1 女 2 男
     */
    private Integer gender;

    private String fullName;//研发部门全名
    private String rds;
    /**
     * 缺失月
     */
    private String lackMonth;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public String getAliasType() {
        return aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
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

    public String getEdLevel() {
        return edLevel;
    }

    public void setEdLevel(String edLevel) {
        this.edLevel = edLevel;
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

    public List<EmployeeEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<EmployeeEntity> entityList) {
        this.entityList = entityList;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public String getBasePosition() {
        return basePosition;
    }

    public void setBasePosition(String basePosition) {
        this.basePosition = basePosition;
    }

    public String getLackMonth() {
        return lackMonth;
    }

    public void setLackMonth(String lackMonth) {
        this.lackMonth = lackMonth;
    }
}
