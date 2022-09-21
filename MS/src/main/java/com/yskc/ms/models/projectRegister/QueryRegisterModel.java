package com.yskc.ms.models.projectRegister;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/8/6 14:41
 * description:查询备案model
 */
public class QueryRegisterModel extends PageParams implements Serializable {


    private String companyName;//客户名称

    private Integer year;//年份

    private String fullPath;//所属部门

    private Integer[] tIds;//技术人员ids

    private String stageKey;//阶段

    private Integer owerId;//业务员id

    private String productName;//项目名称

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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }
}
