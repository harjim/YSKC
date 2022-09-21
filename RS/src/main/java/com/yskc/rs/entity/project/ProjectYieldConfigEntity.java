package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.UserInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:08
 * @Description: 项目试制量配置
 */
@TableName("p_yield_config")
public class ProjectYieldConfigEntity {

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private BigDecimal totalYield;

    private BigDecimal rdYield;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;

    private String deptName;

    private BigDecimal planYield;

    private String unit;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trialDate;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private Date createTime;
    private Date lastUpdateTime;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    @TableField(exist = false)
    private String rdTitle;
    private String trialProduct;


    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date testEndTime;  // 检验结束时间

    private BigDecimal testHour;  //检验工时

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date testStartTime;  // 检验开始时间

    private BigDecimal totalHour = BigDecimal.ZERO;  //运行工时

    private BigDecimal rdHour = BigDecimal.ZERO;   //研发工时

    private BigDecimal trialHour = BigDecimal.ZERO;  //试制工时

    /**
     * 来源：0：导入，添加，1：生成
     */
    private Integer source;

    public static ProjectYieldConfigEntity build(Date now, Integer userId, Integer msUserId, Integer projectId,
                                                 Integer companyId, Date month, String place, String unit,
                                                 BigDecimal current, BigDecimal dayHour, Date curTrialDate,
                                                 Date startTime,Date endTime, Integer source, Date testEndTime,
                                                 BigDecimal testHour,Date testStartTime,BigDecimal rdHour,BigDecimal totalHour) {
        ProjectYieldConfigEntity entity = new ProjectYieldConfigEntity();
        entity.createTime = now;
        entity.lastUpdateTime = now;
        entity.creatorId = userId;
        entity.lastUpdatorId = userId;
        entity.msCreatorId = msUserId;
        entity.msLastUpdatorId = msUserId;
        entity.projectId = projectId;
        entity.companyId = companyId;
        entity.month = month;
        entity.deptName = place;
        entity.unit = unit;
        entity.totalYield = dayHour;
        entity.rdYield = entity.planYield = current;
        entity.trialDate = curTrialDate;
        entity.startTime = startTime;
        entity.endTime = endTime;
        entity.source = source;
        entity.testEndTime = testEndTime;
        entity.testHour = testHour;
        entity.testStartTime = testStartTime;
        entity.trialHour =  entity.rdHour =  rdHour;
        entity.totalHour = totalHour;
        return entity;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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

    public void buildCreate(Date now, UserInfo userInfo) {
        createTime = now;
        creatorId = userInfo.getUserId();
        msCreatorId = userInfo.getMsUserId();
        buildUpdate(now, userInfo);
    }

    public void buildUpdate(Date now, UserInfo userInfo) {
        lastUpdateTime = now;
        msLastUpdatorId = userInfo.getMsUserId();
        lastUpdatorId = userInfo.getUserId();
        companyId = userInfo.getCompanyId();
    }

    public String getTrialProduct() {
        return trialProduct;
    }

    public void setTrialProduct(String trialProduct) {
        this.trialProduct = trialProduct;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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
