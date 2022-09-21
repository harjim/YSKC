package com.yskc.docservice.entity.rs.project;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.entity.rs.tech.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:11
 * @Description: 项目财务排期表
 */
@TableName("p_fina_schedule")
public class ProjectFinaSchedule extends BaseEntity {
    @TableId
    private Integer id;
    private Integer projectId;
    private Integer companyId;
    private String deptName;
    private Date month;
    private BigDecimal workHours;
    private BigDecimal testHour;
    private BigDecimal trialHour;

    public static ProjectFinaSchedule build(BigDecimal testHour, BigDecimal trialHour, BigDecimal workHours, Date month,
                                            Integer projectId, Integer companyId, Date now, Integer userId,
                                            Integer msUserId,String deptName) {
        ProjectFinaSchedule result = new ProjectFinaSchedule();
        result.create(userId,msUserId,now);
        result.companyId = companyId;
        result.projectId =projectId;
        result.month = month;
        result.workHours = workHours;
        result.testHour = testHour;
        result.trialHour = trialHour;
        result.deptName = deptName;
        return  result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getTestHour() {
        return testHour;
    }

    public void setTestHour(BigDecimal testHour) {
        this.testHour = testHour;
    }

    public BigDecimal getTrialHour() {
        return trialHour;
    }

    public void setTrialHour(BigDecimal trialHour) {
        this.trialHour = trialHour;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
