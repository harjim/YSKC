package com.yskc.ms.models.project;

import com.yskc.ms.models.params.PageParams;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 11:15
 * @Description:
 */
public class QueryProjectDetailModel extends PageParams {

    private String pname;

    private String companyName;

    private Integer year;

    private List<Integer> tIds;

    private String groupName;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Integer> gettIds() {
        return tIds;
    }

    public void settIds(List<Integer> tIds) {
        this.tIds = tIds;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
