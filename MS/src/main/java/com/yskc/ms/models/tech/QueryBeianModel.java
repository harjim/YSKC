package com.yskc.ms.models.tech;

import com.yskc.ms.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/27 9:17
 * @Description:
 */
public class QueryBeianModel extends PageParams {

    private String productName;

    private Integer year;

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
