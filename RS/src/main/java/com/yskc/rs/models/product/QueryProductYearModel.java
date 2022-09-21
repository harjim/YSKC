package com.yskc.rs.models.product;

import java.io.Serializable;
import java.util.List;

/**
 * @program: rs
 * @description: 编辑企业产品产值
 * @author: cyj
 * @create: 2022-06-10 16:57
 **/
public class QueryProductYearModel implements Serializable {
    private List<ProductYearModel> models;
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<ProductYearModel> getModels() {
        return models;
    }

    public void setModels(List<ProductYearModel> models) {
        this.models = models;
    }
}
