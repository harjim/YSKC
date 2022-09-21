package com.yskc.rs.models.sysDocument;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.sysDocument
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-23 15:01
 * @Description: 文档查询model
 */
public class QuerySysDocumentModel extends PageParams {

    private Date docMonth;

    private Integer fileType;

    private String fileName;

    private String workshop;

    private Integer year;

    private Integer projectId;

    public Date getDocMonth() {
        return docMonth;
    }

    public void setDocMonth(Date docMonth) {
        this.docMonth = docMonth;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
