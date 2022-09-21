package com.yskc.ms.models.customer;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.customer
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-18 14:54
 * @Description: 查询客户model
 */
public class QueryCustomerModel extends PageParams implements Serializable {

    private String companyName;
    private Integer[] tIds;
    private Integer[] fIds;
    private Integer deptId;
    private Integer year;
    private String addressCode;

    private String industryCode;

    private Integer hasOpen;

    private Integer userId;

    private String fullPath;

    private String productName;
    private Integer companyType;
    private String companyLevel;
    private Boolean unsigned;
    private Date lastUpdateTime;

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(Boolean unsigned) {
        this.unsigned = unsigned;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public Integer getHasOpen() {
        return hasOpen;
    }

    public void setHasOpen(Integer hasOpen) {
        this.hasOpen = hasOpen;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }
}
