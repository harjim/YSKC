package com.yskc.rs.models.docFile;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/4/20 11:44
 * @Description:阶段配置
 */
public class DocFileStageModel implements Serializable {

    private Integer id;

    private Integer seq;

    private String stageKey;

    private Integer docFileId;

    private Boolean autoAdd;

    private Boolean monthly;
    private Boolean mPrefix;
    private Boolean needEdit;

    private String docName;

    private Integer docTemplateId;


    public Integer getDocTemplateId() {
        return docTemplateId;
    }

    public void setDocTemplateId(Integer docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public Boolean getAutoAdd() {
        return autoAdd;
    }

    public void setAutoAdd(Boolean autoAdd) {
        this.autoAdd = autoAdd;
    }

    public Boolean getMonthly() {
        return monthly;
    }

    public void setMonthly(Boolean monthly) {
        this.monthly = monthly;
    }

    public Boolean getmPrefix() {
        return mPrefix;
    }

    public void setmPrefix(Boolean mPrefix) {
        this.mPrefix = mPrefix;
    }

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
