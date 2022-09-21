package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: rs
 * @description: 企业产品产值
 * @author: wjy
 * @create: 2022-06-09 09:05
 **/
@TableName("c_product_year")
public class ProductYearEntity extends BaseEntity implements Serializable{
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer productId;
    private Integer year;
    private BigDecimal output;
    private BigDecimal outputValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getOutput() {
        return output;
    }

    public void setOutput(BigDecimal output) {
        this.output = output;
    }

    public BigDecimal getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigDecimal outputValue) {
        this.outputValue = outputValue;
    }
}
