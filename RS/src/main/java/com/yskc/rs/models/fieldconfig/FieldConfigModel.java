package com.yskc.rs.models.fieldconfig;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 配置明细model
 * @author: wjy
 * @create: 2022-06-14 09:46
 **/
public class FieldConfigModel implements Serializable {

    private Integer id;

    private String config;

    private Integer number;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
