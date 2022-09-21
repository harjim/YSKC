package com.yskc.ms.models.product;

import java.util.Date;

public class ProductModel {

    private int id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 所属地
     */
    private String address;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 申报项目类型
     */
    private Integer declareType;

    private Boolean shouldRegister;//是否备案

    private String[] addressCodeArr;
    private String addressStr;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    public Integer getDeclareType() {
        return declareType;
    }

    public void setDeclareType(Integer declareType) {
        this.declareType = declareType;
    }

    public Boolean getShouldRegister() {
        return shouldRegister;
    }

    public void setShouldRegister(Boolean shouldRegister) {
        this.shouldRegister = shouldRegister;
    }


    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getAddressCodeArr() {
        return addressCodeArr;
    }

    public void setAddressCodeArr(String[] addressCodeArr) {
        this.addressCodeArr = addressCodeArr;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
