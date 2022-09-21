package com.yskc.ms.models.rs;

import com.yskc.ms.models.rs.RsProductStageListModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/14 9:29
 * description:申报项目阶段
 */
public class RsProductStageModel implements Serializable {

    private Integer id;

    private String stageKey;

    private Integer directionId;

    private Integer seq;

    private Date expiryDate;

    private List<RsProductStageListModel> models;

    private Boolean limitChange;//true :不可修改  false:能修改

    public Boolean getLimitChange() {
        return limitChange;
    }

    public void setLimitChange(Boolean limitChange) {
        this.limitChange = limitChange;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<RsProductStageListModel> getModels() {
        return models;
    }

    public void setModels(List<RsProductStageListModel> models) {
        this.models = models;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
