package com.yskc.rs.models.excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-18 10:49
 * @Description: 财务排期导入
 */
public class ProjectFinaScheduleExcel {
    private Integer projectId;
    private String rdTitle;
    private String deptName;
    private String eas;
    private Date month;
    private BigDecimal workHours;
    private BigDecimal testHour;
    private BigDecimal trialHour;

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getEas() {
        return eas;
    }

    public void setEas(String eas) {
        this.eas = eas;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
