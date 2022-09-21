package com.yskc.ms.models.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/2 8:50
 * description:
 */
public class ProjectBasicInfoModel implements Serializable {

    private Integer projectId;

    private ProjectBasicModel basicModel;

    private ProjectFinanceInfoModel financeInfoModel;

    private ProjectTechInfoModel techInfoModel;

    private List<ProjectTimelineModel> timelineModels;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public ProjectBasicModel getBasicModel() {
        return basicModel;
    }

    public void setBasicModel(ProjectBasicModel basicModel) {
        this.basicModel = basicModel;
    }

    public ProjectFinanceInfoModel getFinanceInfoModel() {
        return financeInfoModel;
    }

    public void setFinanceInfoModel(ProjectFinanceInfoModel financeInfoModel) {
        this.financeInfoModel = financeInfoModel;
    }

    public ProjectTechInfoModel getTechInfoModel() {
        return techInfoModel;
    }

    public void setTechInfoModel(ProjectTechInfoModel techInfoModel) {
        this.techInfoModel = techInfoModel;
    }

    public List<ProjectTimelineModel> getTimelineModels() {
        return timelineModels;
    }

    public void setTimelineModels(List<ProjectTimelineModel> timelineModels) {
        this.timelineModels = timelineModels;
    }
}
