package com.yskc.rs.models.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2021/1/25 9:18
 * description:
 */
public class AuditDocFileModel implements Serializable {

    private Integer projectId;

    private List<Integer> docFileId;

    private Integer status;

    private Integer year;

    public List<Integer> getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(List<Integer> docFileId) {
        this.docFileId = docFileId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
