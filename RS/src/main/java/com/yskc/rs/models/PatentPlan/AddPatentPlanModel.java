package com.yskc.rs.models.PatentPlan;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/7/6 14:23
 * description:添加专利立项model
 */
public class AddPatentPlanModel implements Serializable {

    private Integer id;

    private Integer projectId;//项目id

    private String patentName;//申请名称

    private String filePath;//交底书路径

    private String description;//描述

    private String inventor;//发明人

    private String inventorInfo;//发明人信息

    private Integer year;//年份

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getInventorInfo() {
        return inventorInfo;
    }

    public void setInventorInfo(String inventorInfo) {
        this.inventorInfo = inventorInfo;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
