package com.yskc.rs.models.hightech;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 查询高新进度model
 * @author: wjy
 * @create: 2022-05-19 09:58
 **/
public class QueryHighTechProgModel extends PageParams implements Serializable {
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
