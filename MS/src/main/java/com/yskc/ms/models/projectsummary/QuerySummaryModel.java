package com.yskc.ms.models.projectsummary;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/11/10 14:14
 * description:
 */
public class QuerySummaryModel extends PageParams implements Serializable {


    private String companyName;//客户名称

    private Integer year;//年份

    private Integer[] tIds;//技术人员ids

    private Integer owerId;//业务员id

    private Integer[] fIds;//财务人员ids

    private String fullPath;//所属部门
    private String productName;//项目类型

    public Integer[] getfIds() {
        return fIds;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setfIds(Integer[] fIds) {
        this.fIds = fIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

}
