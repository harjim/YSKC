package com.yskc.ms.models.project;

/**
 * Created by hck
 * on 2020/5/20 14:22
 */
public class StepStatusModel {

    private Integer productId;

    private String remake;

    private Integer status;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
