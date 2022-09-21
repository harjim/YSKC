package com.yskc.rs.models.tech;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/14 9:29
 * description:申报项目阶段
 */
public class ProductStageModel implements Serializable {
    private Integer id;

    private String stageKey;

    private Integer directionId;

    private Integer seq;

    private Date expiryDate;

    private List<ProductStageListModel> models;

    private Boolean limitChange;//true :可修改  false:不能修改

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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public List<ProductStageListModel> getModels() {
        return models;
    }

    public void setModels(List<ProductStageListModel> models) {
        this.models = models;
    }

    public Boolean getLimitChange() {
        return limitChange;
    }

    public void setLimitChange(Boolean limitChange) {
        this.limitChange = limitChange;
    }
}
