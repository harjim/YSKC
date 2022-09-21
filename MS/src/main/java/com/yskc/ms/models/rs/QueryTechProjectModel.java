package com.yskc.ms.models.rs;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/15 14:26
 * description:
 */
public class QueryTechProjectModel extends PageParams implements Serializable {

    private String ptName;//项目名称

    private String companyName;//客户名称

    private Integer year;//年份

    private String fullPath;//所属部门

//    private Integer[] tIds;//技术人员ids

    private String addressCode;//地区

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

//    public Integer[] gettIds() {
//        return tIds;
//    }
//
//    public void settIds(Integer[] tIds) {
//        this.tIds = tIds;
//    }
}
