package com.yskc.ms.models.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/2 8:27
 * description:技术服务信息model
 */
public class ProjectTechInfoModel implements Serializable {
    private Integer id;
    private Date startTime;//启动时间
    private Date dockingTime;//对接时间
    private String content;//内容
    private Integer projectId;
    private List<ProjectTimelineModel> models;

    public List<ProjectTimelineModel> getModels() {
        return models;
    }

    public void setModels(List<ProjectTimelineModel> models) {
        this.models = models;
    }

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getDockingTime() {
        return dockingTime;
    }

    public void setDockingTime(Date dockingTime) {
        this.dockingTime = dockingTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
