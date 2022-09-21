package com.yskc.ms.models.product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/28 14:19
 * description:
 */
public class AddStageModel implements Serializable {

    private Integer id;

    private Integer productId;

    private String productName;

  private List<StageModel> stageKeys;

    private Integer projectId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<StageModel> getStageKeys() {
        return stageKeys;
    }

    public void setStageKeys(List<StageModel> stageKeys) {
        this.stageKeys = stageKeys;
    }
}
