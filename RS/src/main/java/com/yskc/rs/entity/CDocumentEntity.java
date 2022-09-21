package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2022/1/14 14:15
 * @Description: 风险核算报告AND核算方法
 * @author: hsx
 */
@TableName("c_document")
public class CDocumentEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7135931781146756950L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer year;

    private Integer type;

    private String data;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
