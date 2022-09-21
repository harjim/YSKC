package com.yskc.rs.models.product;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 产品编码
 * @author: cyj
 * @create: 2022-06-13 15:26
 **/
public class QueryPcodeModel implements Serializable {
    private Integer productId;
    private String pcode;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
