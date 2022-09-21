package com.yskc.ms.models.contract;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-31 09:15
 **/
public class QueryProductModel {
    private Integer customerId;
    private Integer productId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
