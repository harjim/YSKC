package com.yskc.rs.models.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.EmployeeEntity;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class EmployeeModel implements Serializable {
    private Integer id;
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
    private Boolean disabledAtt;
    /**
     * 关联研发考勤id[employeeOpenid.id]
     */
    private Integer eoId;

    private String rdDeptName; //研发部门
    private Integer roleType;
    private String autographUrl;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    private List<EmployeeEntity> entityList;

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

    public String getOldEnumber() {
        return oldEnumber;
    }

    public void setOldEnumber(String oldEnumber) {
        this.oldEnumber = oldEnumber;
    }

    public List<EmployeeEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<EmployeeEntity> entityList) {
        this.entityList = entityList;
    }

    public String getEdLevel() {
        return edLevel;
    }

    public void setEdLevel(String edLevel) {
        this.edLevel = edLevel;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }


    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Boolean getDisabledAtt() {
        return disabledAtt;
    }

    public void setDisabledAtt(Boolean disabledAtt) {
        this.disabledAtt = disabledAtt;
    }

    public Boolean getBindAtt() {
        return eoId != null;
    }


    public void setEoId(Integer eoId) {
        this.eoId = eoId;
    }

    public Integer getEoId() {
        return eoId;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getAutographUrl() {
        return autographUrl;
    }

    public void setAutographUrl(String autographUrl) {
        this.autographUrl = autographUrl;
    }
}
