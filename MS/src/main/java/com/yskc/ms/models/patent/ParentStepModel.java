package com.yskc.ms.models.patent;

import com.yskc.ms.models.project.StepTreeModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/22 9:49
 * description:
 */
public class ParentStepModel {

    private Integer productId;

    private String stepName;

    private String key;

    private List<StepTreeModel> children;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<StepTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<StepTreeModel> children) {
        this.children = children;
    }
}
