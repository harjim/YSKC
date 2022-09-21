package com.yskc.ms.models.customer;

import java.io.Serializable;

/**
 * @program: ms
 * @description: 客户业务员
 * @author: cyj
 * @create: 2022-06-13 11:31
 **/
public class CustomerOwerModel implements Serializable {
    private Integer id;
    private Integer owerId;

    private String owerName;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }
}
