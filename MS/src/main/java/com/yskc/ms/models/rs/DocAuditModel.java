package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/3/1 10:26
 * @Description:项目审核
 */
public class DocAuditModel implements Serializable {

    private Integer projectId;

    private Integer pDocFileId;//模板id

    private Integer docFileId;//文件id

    private Integer beginYear;

    private Integer endYear;

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
