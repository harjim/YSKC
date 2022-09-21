package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工考勤使用表
 *
 * @author huronghua
 */
@TableName("p_attendance_used")
public class ProjectAttendanceUsed implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private Integer companyId;
    private String enumber;
    private BigDecimal workHour;
    private BigDecimal remainHours;
    private String timeRange;
    private Date workDate;
    private Date createTime;
    private Integer creatorId;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public static ProjectAttendanceUsed build(Integer usedId, BigDecimal remainHours, BigDecimal usedWorkHour, String timeRange) {
        ProjectAttendanceUsed used = new ProjectAttendanceUsed();
        used.id = usedId;
        used.remainHours = remainHours;
        used.workHour = usedWorkHour;
        used.timeRange = timeRange;
        return used;
    }

    public static ProjectAttendanceUsed build(Integer usedId, BigDecimal remainHours, BigDecimal usedWorkHour, String timeRange,
                                              Integer companyId, Integer userId, Integer msUserId, Date now,String enumber,Date workDate) {
        ProjectAttendanceUsed used = build(usedId, remainHours, usedWorkHour, timeRange);
        used.companyId = companyId;
        used.createTime = used.lastUpdateTime = now;
        used.creatorId = used.lastUpdatorId = userId;
        used.msCreatorId = used.msLastUpdatorId = msUserId;
        used.enumber = enumber;
        used.workDate = workDate;
        return used;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }


    public void subOrSetRemainHours(BigDecimal remainHours) {
        if (this.remainHours != null) {
            this.remainHours = this.remainHours.subtract(remainHours);
        } else {
            setRemainHours(remainHours);
        }
    }

    public void setUpdate(Date now, Integer userId, Integer msUserId) {
        this.lastUpdateTime = now;
        this.lastUpdatorId = userId;
        this.msLastUpdatorId = msUserId;
    }

    public void setCreate(String enumber, Integer companyId) {
        this.enumber = enumber;
        this.companyId = companyId;
        this.createTime = this.lastUpdateTime;
        this.creatorId = this.lastUpdatorId;
        this.msCreatorId = this.msLastUpdatorId;
    }

    public void addTimeRange(String range) {
        timeRange = StringUtils.hasLength(timeRange) ? timeRange + ","+ range : range;

    }
}
