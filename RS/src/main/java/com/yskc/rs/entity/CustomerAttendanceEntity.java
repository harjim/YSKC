package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.common.utils.DateUtil;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:02
 * @Description: 员工考勤
 */
@TableName("c_attendance")
public class CustomerAttendanceEntity implements Serializable {
    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    private Integer msLastUpdatorId;
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
    private Date workDate;
    /**
     *
     */
    private BigDecimal workHour;
    /**
     *
     */
    private Date onTime1;
    /**
     *
     */
    private Date offTime1;
    private Date onTime2;
    /**
     *
     */
    private Date offTime2;
    private Date onTime3;
    /**
     *
     */
    private Date offTime3;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    @Size(max = 200, message = "备注不能超过200个字")
    private String remark;

    private String deptName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
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

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getWorkDate() {
        return workDate != null ? DateUtil.getDayBegin(workDate) : null;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Date getOnTime1() {
        return onTime1;
    }

    public void setOnTime1(Date onTime1) {
        this.onTime1 = onTime1;
    }

    public Date getOffTime1() {
        return offTime1;
    }

    public void setOffTime1(Date offTime1) {
        this.offTime1 = offTime1;
    }

    public Date getOnTime2() {
        return onTime2;
    }

    public void setOnTime2(Date onTime2) {
        this.onTime2 = onTime2;
    }

    public Date getOffTime2() {
        return offTime2;
    }

    public void setOffTime2(Date offTime2) {
        this.offTime2 = offTime2;
    }

    public Date getOnTime3() {
        return onTime3;
    }

    public void setOnTime3(Date onTime3) {
        this.onTime3 = onTime3;
    }

    public Date getOffTime3() {
        return offTime3;
    }

    public void setOffTime3(Date offTime3) {
        this.offTime3 = offTime3;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
