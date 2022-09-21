package com.yskc.rs.models.hightech;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:42
 * @Description: 查询高品model
 */
public class QueryHighTechModel extends PageParams implements Serializable {

    private Integer year;

    private String tecIndustry;

    private String hcode;

    private String hname;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }
}
