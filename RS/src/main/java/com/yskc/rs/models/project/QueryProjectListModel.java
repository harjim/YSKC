package com.yskc.rs.models.project;

import com.yskc.rs.models.params.PageParams;

/**
 * Created by hck
 * on 2020/7/17 16:50
 * description:
 */
public class QueryProjectListModel{

    private Integer year;

    private String pname;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
