package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/15 15:21
 * description:申报项目model
 */
public class RsProductListModel implements Serializable {

    private Integer id;

    private String productName;

    private String addressCode;

    private Integer year;

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
