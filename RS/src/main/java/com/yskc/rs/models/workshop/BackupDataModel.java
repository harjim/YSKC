package com.yskc.rs.models.workshop;

import com.yskc.rs.entity.DocFileTemplateEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/3 15:56
 * @Description:备查资料列表
 */
public class BackupDataModel implements Serializable {

    private Integer id;

    private Integer seq;

    private String docName;

    private Integer docFileId;

    private Boolean isDocFile;

    private Integer auditStatus;

    private List<DocFileTemplateEntity> templates;//文档模板列表

    public List<DocFileTemplateEntity> getTemplates() {
        return templates;
    }

    public void setTemplates(List<DocFileTemplateEntity> templates) {
        this.templates = templates;
    }

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

    public BackupDataModel() {
    }

    public BackupDataModel(Integer id,Integer seq, String docName, Integer docFileId,Boolean isDocFile,List<DocFileTemplateEntity> templates) {
        this.id=id;
        this.seq = seq;
        this.docName = docName;
        this.docFileId = docFileId;
        this.isDocFile=isDocFile;
        this.templates=templates;
        if(id==3){
            this.auditStatus=1;
        }else {
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
