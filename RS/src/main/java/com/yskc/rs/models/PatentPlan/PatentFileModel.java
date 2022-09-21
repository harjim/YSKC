package com.yskc.rs.models.PatentPlan;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 17:09
 * @Description:
 */
public class PatentFileModel implements Serializable {

    private Integer id;

    private String fileName;

    private String filePath;

    private Integer fileType;

    private Integer patentPlanId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }
}
