package com.yskc.rs.models.tech;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/19 14:59
 * description:技改项目文件
 */
public class ProjectListModel implements Serializable {
    private Integer id;
    private String filePath;
    private Integer stageListId;
    private String fileName;
    private Integer projectId;
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStageListId() {
        return stageListId;
    }

    public void setStageListId(Integer stageListId) {
        this.stageListId = stageListId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
