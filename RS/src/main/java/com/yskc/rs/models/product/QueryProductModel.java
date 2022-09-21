package com.yskc.rs.models.product;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 查询企业产品
 * @author: wjy
 * @create: 2022-06-09 09:01
 **/
public class QueryProductModel extends PageParams implements Serializable {
    private String pname;
    private String pcode;
    private String model;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
