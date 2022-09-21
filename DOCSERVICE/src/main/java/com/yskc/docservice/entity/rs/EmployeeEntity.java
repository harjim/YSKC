package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.docservice.models.rs.RsUserInfo;
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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveDate;
    /**
     * 专业
     */
    private String specialities;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String deptName;

    private Boolean disabledAtt;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
//
//    public static EmployeeEntity build(RsUserInfo info, Date now, Integer deptId, InitMemberExcel excel) {
//        EmployeeEntity employeeEntity = new EmployeeEntity();
//        employeeEntity.setEntityInfo(excel.getEname(), excel.getEnumber(), excel.getTitle(), excel.getSpecialities(), excel.getBirthday(), excel.getEdate(), excel.getPosition());
//        employeeEntity.setEntityBase(info, now, deptId);
//        employeeEntity.gender = -1;
//        return employeeEntity;
//    }

    public static EmployeeEntity build(RsUserInfo info, Date now, Integer deptId, String ename, String enumber, String deptName) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEntityInfo(ename, enumber, null, null, null, null, "");
        employeeEntity.setEntityBase(info, now, deptId);
        employeeEntity.deptName = deptName;
        employeeEntity.gender = -1;
        return employeeEntity;
    }

    void setEntityInfo(String ename, String enumber, String title, String specialities, Date birthday, Date edate, String position) {
        this.ename = ename;
        this.enumber = enumber;
        this.title = title;
        this.specialities = specialities;
        this.birthday = birthday;
        this.edate = edate;
        this.position = StringUtils.isEmpty(position) ? "" : position;
    }

    void setEntityBase(RsUserInfo info, Date now, Integer deptId) {
        this.deptId = deptId;
        this.createTime = now;
        this.lastUpdateTime = now;
        this.msCreatorId = info.getMsUserId();
        this.msLastUpdatorId = info.getMsUserId();
        this.creatorId = info.getUserId();
        this.lastUpdatorId = info.getUserId();
        this.companyId = info.getCompanyId();
        this.eduLevel = 0;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getDisabledAtt() {
        return disabledAtt;
    }

    public void setDisabledAtt(Boolean disabledAtt) {
        this.disabledAtt = disabledAtt;
    }
}
