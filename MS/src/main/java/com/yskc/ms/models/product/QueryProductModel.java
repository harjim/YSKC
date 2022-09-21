package com.yskc.ms.models.product;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/5 9:20
 * description:
 */
public class QueryProductModel extends PageParams {

    private Integer year;
    private String productName;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
