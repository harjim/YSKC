package com.yskc.ms.models.ProjectTechStage;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/28 16:44
 * description:
 */
public class ProjectStageModel implements Serializable {

    private Integer id;//项目阶段id

    private Integer projectStageId;//关联项目类型阶段的id

    private Integer order;

    private Integer stageKey;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date deadline;//截止日期

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getProjectStageId() {
        return projectStageId;
    }

    public void setProjectStageId(Integer projectStageId) {
        this.projectStageId = projectStageId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStageKey() {
        return stageKey;
    }

    public void setStageKey(Integer stageKey) {
        this.stageKey = stageKey;
    }
}
