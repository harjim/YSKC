package com.yskc.ms.models.rs.summary;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/6/5 11:10
 * @Description:
 */
public class BackupDataListModel implements Serializable {


    private Integer id;

    private Integer seq;

    private String docName;

    private Integer docFileId;

    private Boolean isDocFile;

    private Integer auditStatus;


    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getDocFile() {
        return isDocFile;
    }

    public void setDocFile(Boolean docFile) {
        isDocFile = docFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BackupDataListModel() {
    }

    public BackupDataListModel(Integer id, Integer seq, String docName, Integer docFileId, Boolean isDocFile) {
        this.id = id;
        this.seq = seq;
        this.docName = docName;
        this.docFileId = docFileId;
        this.isDocFile = isDocFile;
        if (id == 3) {
            this.auditStatus = 1;
        } else {
            this.auditStatus = 5;
        }
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
