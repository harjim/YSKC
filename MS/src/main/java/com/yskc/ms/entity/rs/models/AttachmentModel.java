package com.yskc.ms.entity.rs.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 15:02
 * @Description:文档附件model
 */
public class AttachmentModel implements Serializable {

    private Integer id;

    private Integer projectId;

    private Integer docFileId;

    private String fileName;

    private String filePath;

    private Date uploadTime;

    private String place;
    private Integer fileType;

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
