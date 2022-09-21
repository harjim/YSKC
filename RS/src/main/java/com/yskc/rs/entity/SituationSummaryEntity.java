package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.projectSummary.SituationSummaryModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 11:08
 * @Description: 项目情况汇总
 */
@TableName("p_situation_summary")
public class SituationSummaryEntity implements Serializable {
    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer year;
    private String annualReview;// 年度回顾总述
    private String teamEffort;//团队努力情况
    private String scheduleReview;// 进度计划回顾
    private String valueRealization;// 价值实现情况
    private String improvementPoints;// 后续需加强点
    private String rdDepts;// 研发参与部门
    private String rdContent;// 研发内容
    private String achievements;// 取得的成效

    public SituationSummaryEntity() {
    }

    public SituationSummaryEntity(SituationSummaryModel model,Integer msUserId,Integer userId,Date date,Integer companyId) {
        this.creatorId = userId;
        this.lastUpdatorId = userId;
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.year = model.getYear();
        this.annualReview = model.getAnnualReview();
        this.teamEffort = model.getTeamEffort();
        this.scheduleReview = model.getScheduleReview();
        this.valueRealization = model.getValueRealization();
        this.improvementPoints = model.getImprovementPoints();
        this.rdDepts = model.getRdDepts();
        this.rdContent = model.getRdContent();
        this.achievements = model.getAchievements();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAnnualReview() {
        return annualReview;
    }

    public void setAnnualReview(String annualReview) {
        this.annualReview = annualReview;
    }

    public String getTeamEffort() {
        return teamEffort;
    }

    public void setTeamEffort(String teamEffort) {
        this.teamEffort = teamEffort;
    }

    public String getScheduleReview() {
        return scheduleReview;
    }

    public void setScheduleReview(String scheduleReview) {
        this.scheduleReview = scheduleReview;
    }

    public String getValueRealization() {
        return valueRealization;
    }

    public void setValueRealization(String valueRealization) {
        this.valueRealization = valueRealization;
    }

    public String getImprovementPoints() {
        return improvementPoints;
    }

    public void setImprovementPoints(String improvementPoints) {
        this.improvementPoints = improvementPoints;
    }

    public String getRdDepts() {
        return rdDepts;
    }

    public void setRdDepts(String rdDepts) {
        this.rdDepts = rdDepts;
    }

    public String getRdContent() {
        return rdContent;
    }

    public void setRdContent(String rdContent) {
        this.rdContent = rdContent;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
}
