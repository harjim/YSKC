package com.yskc.rs.models.projectyieldconfig;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:28
 * @Description: 项目试制量配置model
 */
public class ProjectYieldConfigModel {

    private Integer id;

    private String deptName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalYield;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdYield;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal planYield;
    private String unit;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date trialDate;

    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date month;

    private String rdTitle;

    private String pname;

    private Integer projectId;
    private String trialProduct;
    private Boolean del;

    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date testEndTime;  // 检验结束时间

    private BigDecimal testHour;  //检验工时

    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date testStartTime;  // 检验开始时间

    private BigDecimal totalHour = BigDecimal.ZERO;  //运行工时

    private BigDecimal rdHour = BigDecimal.ZERO;   //研发工时

    private BigDecimal trialHour;   //试制工时


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    public BigDecimal getPlanYield() {
        return planYield;
    }

    public void setPlanYield(BigDecimal planYield) {
        this.planYield = planYield;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTrialProduct() {
        return trialProduct;
    }

    public void setTrialProduct(String trialProduct) {
        this.trialProduct = trialProduct;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Date getTestEndTime() {
        return testEndTime;
    }

    public void setTestEndTime(Date testEndTime) {
        this.testEndTime = testEndTime;
    }

    public BigDecimal getTestHour() {
        return testHour;
    }

    public void setTestHour(BigDecimal testHour) {
        this.testHour = testHour;
    }

    public Date getTestStartTime() {
        return testStartTime;
    }

    public void setTestStartTime(Date testStartTime) {
        this.testStartTime = testStartTime;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getTrialHour() {
        return trialHour;
    }

    public void setTrialHour(BigDecimal trialHour) {
        this.trialHour = trialHour;
    }
}
