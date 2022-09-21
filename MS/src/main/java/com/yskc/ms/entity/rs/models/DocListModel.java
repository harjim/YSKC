package com.yskc.ms.entity.rs.models;

import com.yskc.ms.models.SysDocumentModel;

import java.util.List;

public class DocListModel {
    private Integer id;
    private Integer listType;
    private String docName;
    private String desciption;
    private Integer seq;
    private Integer companyId;
    private List<SysDocumentModel> docList;
    private String classify;
    private String subClassify;
    private String rdActivities;
    private String samplePath;
    private Integer childType;
    private String operators;

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getSubClassify() {
        return subClassify;
    }

    public void setSubClassify(String subClassify) {
        this.subClassify = subClassify;
    }

    public String getRdActivities() {
        return rdActivities;
    }

    public void setRdActivities(String rdActivities) {
        this.rdActivities = rdActivities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath;
    }

    public Integer getChildType() {
        return childType;
    }

    public void setChildType(Integer childType) {
        this.childType = childType;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public List<SysDocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(List<SysDocumentModel> docList) {
        this.docList = docList;
    }
}
