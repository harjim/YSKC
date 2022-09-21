package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 15:41
 * @Description:
 */
public class QueryBeianModel extends PageParams {

    private String pname;

    private String productName;

    private Integer year;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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
