package com.yskc.ms.entity.rs.models;

import com.yskc.ms.models.project.StepTreeModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/20 9:23
 */
public class StepResultModel {

    private Integer productId;

    private List<StepTreeModel> data;

    private Integer status;

    private String remark;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<StepTreeModel> getData() {
        return data;
    }

    public void setData(List<StepTreeModel> data) {
        this.data = data;
    }
}
