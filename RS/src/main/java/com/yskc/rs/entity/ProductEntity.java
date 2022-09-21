package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rs
 * @description: 企业产品
 * @author: wjy
 * @create: 2022-06-09 08:54
 **/
@TableName("c_product")
public class ProductEntity extends BaseEntity {
    @TableId
    private Integer id;
    private Integer companyId;
    private String pname;
    private String pcode;
    private Date creationDate;
    private String model;
    private String unit;
    private String parameter;
    private String features;
    private String mainRaw;
    private String comparison;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getMainRaw() {
        return mainRaw;
    }

    public void setMainRaw(String mainRaw) {
        this.mainRaw = mainRaw;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
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
