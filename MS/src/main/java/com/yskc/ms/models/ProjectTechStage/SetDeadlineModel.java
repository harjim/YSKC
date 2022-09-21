package com.yskc.ms.models.ProjectTechStage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/30 8:18
 * description:
 */
public class SetDeadlineModel implements Serializable {

    private List<Integer> projectIds;

    private Date deadline;//截止时间

    private String stageKey;//阶段

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }
}
