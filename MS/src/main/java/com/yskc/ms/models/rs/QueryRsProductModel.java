package com.yskc.ms.models.rs;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/13 11:25
 * description:
 */
public class QueryRsProductModel extends PageParams implements Serializable {

    private String addressCode;

    private Integer year;

    private Integer pType;

    private Integer pLevel;

    private String productName;

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

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Integer getpLevel() {
        return pLevel;
    }

    public void setpLevel(Integer pLevel) {
        this.pLevel = pLevel;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
