package com.yskc.ms.models.projectAudit;

import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.models.qualityscore.QualityScoreModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.projectAudit
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-06 08:38
 * @Description: 批量审核model
 */
public class BatchAuditModel {

    /**
     * 文档id，审核文档
     */
    private List<Integer> docFileIds;

    private String suggestion;

    private Boolean pass;

    private Integer projectId;

    private List<Integer> moduleIds;
    /**
     * 查新报告/项目批量审核 projectIds+moduleId
     */
    private List<Integer> projectIds;

    private Integer moduleId;

    private List<Integer> instanceIds;
    /**
     * 专利批量审核
     */
    private List<Integer> patentIds;

    /**
     * 提案批量审核
     */
    private List<Integer> proposalIds;

    /**
     * 成果批量审核
     */
    private List<Integer> achievementIds;

    /**
     * 立项报告批量
     */
    private List<ProjectDocFileEntity> projectDocFiles;

    private Integer status;

    private QualityScoreModel model;
    private Boolean scoring;

    public Boolean getScoring() {
        return scoring;
    }

    public void setScoring(Boolean scoring) {
        this.scoring = scoring;
    }

    public QualityScoreModel getModel() {
        return model;
    }

    public void setModel(QualityScoreModel model) {
        this.model = model;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public List<Integer> getPatentIds() {
        return patentIds;
    }

    public void setPatentIds(List<Integer> patentIds) {
        this.patentIds = patentIds;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public List<Integer> getDocFileIds() {
        return docFileIds;
    }

    public void setDocFileIds(List<Integer> docFileIds) {
        this.docFileIds = docFileIds;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Integer> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public List<Integer> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<Integer> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public List<Integer> getProposalIds() {
        return proposalIds;
    }

    public void setProposalIds(List<Integer> proposalIds) {
        this.proposalIds = proposalIds;
    }

    public List<Integer> getAchievementIds() {
        return achievementIds;
    }

    public void setAchievementIds(List<Integer> achievementIds) {
        this.achievementIds = achievementIds;
    }

    public List<ProjectDocFileEntity> getProjectDocFiles() {
        return projectDocFiles;
    }

    public void setProjectDocFiles(List<ProjectDocFileEntity> projectDocFiles) {
        this.projectDocFiles = projectDocFiles;
    }
}
