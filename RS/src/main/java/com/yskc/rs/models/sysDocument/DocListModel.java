package com.yskc.rs.models.sysDocument;

import java.util.List;

public class DocListModel {
    private Integer id;
    private Integer listType;
    private String docName;
    private String desciption;
    private Integer seq;
    private Integer companyId;
    private List<SysDocumentModel> docList;
    private List<MonthModel> monthModelList;
    ///////////////
    private String classify;
    private String subClassify;
    private String rdActivities;
    private String samplePath;
    private String operators;

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

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

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath;
    }

    public List<SysDocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(List<SysDocumentModel> docList) {
        this.docList = docList;
    }

    public List<MonthModel> getMonthModelList() {
        return monthModelList;
    }

    public void setMonthModelList(List<MonthModel> monthModelList) {
        this.monthModelList = monthModelList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
