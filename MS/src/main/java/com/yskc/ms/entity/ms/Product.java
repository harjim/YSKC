package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @Author: yzh
 * @Description: 产品表(项目类型)
 */

@TableName("product")
public class Product {
    private static final long serialVersionUID = 1L;
    @TableId
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
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    private Boolean shouldRegister;//是否备案

    private Integer productType;

    private Integer declareType;//申报项目类型

    public Integer getDeclareType() {
        return declareType;
    }

    public void setDeclareType(Integer declareType) {
        this.declareType = declareType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Boolean getShouldRegister() {
        return shouldRegister;
    }

    public void setShouldRegister(Boolean shouldRegister) {
        this.shouldRegister = shouldRegister;
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
