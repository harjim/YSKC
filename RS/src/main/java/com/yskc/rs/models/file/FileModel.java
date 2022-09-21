package com.yskc.rs.models.file;

/**
 * 文件
 * @author huronghua
 *
 */
public class FileModel {
    private String fileName;

    private Integer type;

    private Integer projectId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
