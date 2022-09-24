package com.yskc.docservice.models;

public class DocParam {
    private Integer projectId;
    private Integer[] pDocFileId;
    private Integer currentYear;
    private Integer exportType;
    // 导出签名
    private Boolean importFooterName;
    // 导出页码
    private Boolean pageNum;
    // 导出页眉
    private Boolean header;
    // 导出目录
    private Boolean catalogue;
    // 导出封面
    private Boolean cover;

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

    public Boolean getPageNum() {
        return pageNum;
    }

    public void setPageNum(Boolean pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Boolean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Boolean catalogue) {
        this.catalogue = catalogue;
    }

    public Boolean getCover() {
        return cover;
    }

    public void setCover(Boolean cover) {
        this.cover = cover;
    }
}
