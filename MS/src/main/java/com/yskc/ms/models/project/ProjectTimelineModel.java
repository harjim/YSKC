package com.yskc.ms.models.project;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/2 8:46
 * description:项目服务时间线
 */
public class ProjectTimelineModel implements Serializable {

    private Integer id;
    private Integer projectId;
    private Integer itemType;//类型
    private Date beginTime;//开始时间
    private Date endTime;//结束时间


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    private String result;//结果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }


    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
