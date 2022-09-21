package com.yskc.ms.models.projectAudit;

import com.yskc.ms.models.qualityscore.QualityScoreModel;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-14 15:32
 **/
public class CheckAuditModel {
    private QualityScoreModel model;
    private Boolean scoring;
    private String suggestion;
    private Boolean pass;
    /**
     * 查新报告/项目批量审核 projectIds+moduleId
     */
    private List<Integer> projectIds;

    public QualityScoreModel getModel() {
        return model;
    }

    public void setModel(QualityScoreModel model) {
        this.model = model;
    }

    public Boolean getScoring() {
        return scoring;
    }

    public void setScoring(Boolean scoring) {
        this.scoring = scoring;
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

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }
}
