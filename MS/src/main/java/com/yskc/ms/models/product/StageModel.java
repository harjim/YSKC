package com.yskc.ms.models.product;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/28 16:29
 * description:
 */
public class StageModel implements Serializable {

    private Integer id;

    private Integer order;

    private String stageKey;

    private Integer productId;

    private Integer projectStageId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;//截止日期

    public Integer getProjectStageId() {
        return projectStageId;
    }

    public void setProjectStageId(Integer projectStageId) {
        this.projectStageId = projectStageId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }
}
