package com.yskc.docservice.models.rs;

import io.swagger.models.auth.In;

import java.time.LocalDateTime;
import java.util.Date;

public class PDocFile {
    private Integer id;
    private String docFileName;
    private String docTemplateName;
    private String data;
    private Date month;
    private Boolean deleted;
    private String stage;
    private Integer docFileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getDocTemplateName() {
        return docTemplateName;
    }

    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
