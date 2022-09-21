package com.yskc.ms.models;


import com.yskc.ms.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 14:13
 * @Description:
 */
public class QueryProposalModel extends PageParams {

    private Integer companyId;

    private Integer year;

    private String title;

    private String remark;

    private Integer type;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
