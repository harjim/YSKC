package com.yskc.rs.entity.project;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.config.Constant;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigInfoModel;
import com.yskc.rs.utils.AttDataUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-16 08:40:51
 */
@TableName("p_attendance")
public class ProjectAttendance implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    private String enumber;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Integer attendanceDataId;

    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer creatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private BigDecimal workHour;
    private Date workDate;
    private Date onTime1;

    /**
     * 下班时间1
     */
    private Date offTime1;

    /**
     * 上班时间2
     */

    private Date onTime2;

    /**
     * 下班时间2
     */
    private Date offTime2;

    /**
     * 上班时间3
     */
    private Date onTime3;

    /**
     * 下班时间3
     */
    private Date offTime3;

    private Boolean owner;

    private String content;

    public static ProjectAttendance build(String enumber, Integer companyId, Integer projectId, Date workDate, Integer userId, Integer msUserId, Date now) {
        ProjectAttendance attendance = new ProjectAttendance();
        attendance.enumber = enumber;
        attendance.companyId = companyId;
        attendance.projectId = projectId;
        attendance.workDate = workDate;
        attendance.workHour = BigDecimal.ZERO;
        attendance.createTime = attendance.lastUpdateTime = now;
        attendance.creatorId = attendance.lastUpdatorId = userId;
        attendance.msCreatorId = attendance.msLastUpdatorId = msUserId;
        return attendance;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setAttendanceDataId(Integer attendanceDataId) {
        this.attendanceDataId = attendanceDataId;
    }

    public Integer getAttendanceDataId() {
        return attendanceDataId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public BigDecimal getWorkHour() {
        return workHour == null ? BigDecimal.ZERO : workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
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

    public void addOrSetWorkHour(BigDecimal workHour) {
        if (this.workHour != null) {
            this.workHour = this.workHour.add(workHour);
        } else {
            setWorkHour(workHour);
        }
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addTime(ProjectYieldConfigInfoModel item, ProjectAttendanceUsed used) {
        BigDecimal sumWorkHour = this.workHour.add(item.getAgg());
        this.workHour = sumWorkHour.compareTo(AttDataUtils.DAY_HOUR) > 0 ? this.workHour : sumWorkHour;
        BigDecimal sumUsedHour = used.getWorkHour().add(item.getAgg());
        if (sumUsedHour.compareTo(AttDataUtils.DAY_HOUR) <= 0) {
            used.setWorkHour(sumUsedHour);
        }
        used.setRemainHours(used.getWorkHour().compareTo(AttDataUtils.DEFAULT_MAX_HOUR) >= 0 ? BigDecimal.ZERO : AttDataUtils.DEFAULT_MAX_HOUR.subtract(used.getWorkHour()));
        setTimes(item.getStartTime(), item.getEndTime(), used);
        setTimes(item.getTestStartTime(), item.getTestEndTime(), used);
    }

    private void setTimes(Date start, Date end, ProjectAttendanceUsed used) {
        if (start == null || end == null) {
            return;
        }
        used.addTimeRange(DateUtil.formatTime(start) + Constant.HYPHEN + DateUtil.formatTime(end));
        if (this.onTime1 == null) {
            this.onTime1 = start;
            this.offTime1 = end;
        } else if (this.onTime2 == null) {
            this.onTime2 = start;
            this.offTime2 = end;
        } else if (this.onTime3 == null) {
            this.onTime3 = start;
            this.offTime3 = end;
        }
    }
}
