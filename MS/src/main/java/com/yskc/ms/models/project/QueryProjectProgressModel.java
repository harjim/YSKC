package com.yskc.ms.models.project;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 16:15
 * @Description: 项目进度查询model
 */
public class QueryProjectProgressModel extends PageParams implements Serializable {

    private String productName;
    private String companyName;
    private String groupName;
    private Integer[] tIds;
    private Integer[] fIds;
    private Integer deptId;
    private Integer year;

    private Integer owerId;//业务员
    private String companyLevel;
    private Integer sign;//1:导出项目进度 2：导出归集审核


    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }

    public Integer[] getfIds() {
        return fIds;
    }

    public void setfIds(Integer[] fIds) {
        this.fIds = fIds;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }
}
