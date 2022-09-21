package com.yskc.rs.models.projectfinaschedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectfinaschedule
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-18 08:34
 * @Description: 项目试验试制实际工时model
 */
public class ProjectFinaScheduleModel {
    private Integer id;
    private Integer projectId;
    private String rdTitle;
    private String deptName;
    private String eas;
    private BigDecimal workHours;
    private BigDecimal testHour;
    private BigDecimal trialHour;
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date month;
    private String pname;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEas() {
        return eas;
    }

    public void setEas(String eas) {
        this.eas = eas;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
