package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-10 10:44:40
 */
@TableName("employee")
public class EmployeeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String enumber;
    /**
     *
     */
    private String ename;
    /**
     *
     */
    private Integer deptId;
    /**
     *
     */
    private String position;
    /**
     *
     */
    private String idNumber;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date edate;
    /**
     *
     */
    private Integer eduLevel;

    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;


    private String title;


    private Integer lastUpdatorId;

    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    /**
     * 1 女 2 男
     */
    private Integer gender;
    @TableField(strategy= FieldStrategy.IGNORED)
    private Date leaveDate;
    /**
     * 专业
     */
    private String specialities;

    @TableField(strategy= FieldStrategy.IGNORED)
    private Date birthday;

    private String deptName;

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

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
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

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    private static void setEntityInfo(EmployeeEntity employeeEntity, String ename, String enumber, String title, String specialities, Date birthday, Date edate, String position) {
        employeeEntity.ename = ename;
        employeeEntity.enumber = enumber;
        employeeEntity.title = title;
        employeeEntity.specialities = specialities;
        employeeEntity.birthday = birthday;
        employeeEntity.edate = edate;
        employeeEntity.position = StringUtils.isEmpty(position) ? "" : position;
    }


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
