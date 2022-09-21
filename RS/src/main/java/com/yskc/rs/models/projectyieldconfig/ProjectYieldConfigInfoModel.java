package com.yskc.rs.models.projectyieldconfig;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 10:46
 * @Description: 试制信息
 */
public class ProjectYieldConfigInfoModel {
    private Integer projectId;
    private Date trialDate;
    /**
     * 总量/总工时
     */
    private BigDecimal total;
    /**
     * 研发量/研发工时
     */
    private BigDecimal agg;
    /**
     * 试制工时
     */
    private BigDecimal trialHour;
    private Date startTime;
    private Date endTime;
    private Date testStartTime;
    private Date testEndTime;
    private String deptName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTestStartTime() {
        return testStartTime;
    }

    public void setTestStartTime(Date testStartTime) {
        this.testStartTime = testStartTime;
    }

    public Date getTestEndTime() {
        return testEndTime;
    }

    public void setTestEndTime(Date testEndTime) {
        this.testEndTime = testEndTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAgg() {
        return agg;
    }

    public void setAgg(BigDecimal agg) {
        this.agg = agg;
    }

    public BigDecimal getTrialHour() {
        return trialHour;
    }

    public void setTrialHour(BigDecimal trialHour) {
        this.trialHour = trialHour;
    }
}
