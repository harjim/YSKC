package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目人员考勤
 * @author huronghua
 */
@TableName("p_attendance_month")
public class ProjectAttendanceMonth implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 项目Id
     */
    private Integer projectId;
    /**
     * 月份
     */
    private Date month;
    /**
     * 当前考勤数据(小时)
     */
    private String hoursAttData;
    /**
     * 创建人
     */
    private Integer creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后操作人id
     */
    private Integer lastUpdatorId;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;


    private String personAttData;

    private  Integer msCreatorId;
    private  Integer msLastUpdatorId;

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

    public String getPersonAttData() {
        return personAttData;
    }

    public void setPersonAttData(String personAttData) {
        this.personAttData = personAttData;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getHoursAttData() {
        return hoursAttData;
    }

    public void setHoursAttData(String hoursAttData) {
        this.hoursAttData = hoursAttData;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
