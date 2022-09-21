package com.yskc.rs.models.projectSummary;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:52
 * @Description:项目情况总结model
 */
public class SituationSummaryModel implements Serializable {
    private Integer id;

    private Integer year;

    private String annualReview;// 年度回顾总述
    private String teamEffort;//团队努力情况
    private String scheduleReview;// 进度计划回顾
    private String valueRealization;// 价值实现情况
    private String improvementPoints;// 后续需加强点
    private String rdDepts;// 研发参与部门
    private String rdContent;// 研发内容
    private String achievements;// 取得的成效

    private List<ProblemSummaryModel> problemSummarys;//问题及方案列表

    private List<ProjectSituationModel> projectSituations;//项目情况列表（查询返回参数）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ProblemSummaryModel> getProblemSummarys() {
        return problemSummarys;
    }

    public void setProblemSummarys(List<ProblemSummaryModel> problemSummarys) {
        this.problemSummarys = problemSummarys;
    }

    public List<ProjectSituationModel> getProjectSituations() {
        return projectSituations;
    }

    public void setProjectSituations(List<ProjectSituationModel> projectSituations) {
        this.projectSituations = projectSituations;
    }
}
