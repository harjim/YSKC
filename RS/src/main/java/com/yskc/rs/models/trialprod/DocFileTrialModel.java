package com.yskc.rs.models.trialprod;

/**
 * Created by hck
 * on 2020/7/3 14:50
 * description:
 */
public class DocFileTrialModel {
    private Integer id;

    private Integer pdocFileId;

    private Integer trialId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPdocFileId() {
        return pdocFileId;
    }

    public void setPdocFileId(Integer pdocFileId) {
        this.pdocFileId = pdocFileId;
    }

    public Integer getTrialId() {
        return trialId;
    }

    public void setTrialId(Integer trialId) {
        this.trialId = trialId;
    }
}
