package com.yskc.docservice.models.rs.projectdocfile;

public class DocFileTemplateModel {

    private Integer id;

    private Integer docFileId;

    private String templateName;

    private String docTemplateName;//过程文档模板名

    public String getDocTemplateName() {
        return docTemplateName;
    }

    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
