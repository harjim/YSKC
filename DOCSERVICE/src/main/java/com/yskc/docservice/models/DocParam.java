package com.yskc.docservice.models;

import io.swagger.models.auth.In;

public class DocParam {
    private Integer projectId;
    private Integer[] pDocFileId;
    private Integer currentYear;
    private Integer exportType;
    private Boolean importFooterName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer[] getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer[] pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Boolean getImportFooterName() {
        return importFooterName;
    }

    public void setImportFooterName(Boolean importFooterName) {
        this.importFooterName = importFooterName;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }
}
