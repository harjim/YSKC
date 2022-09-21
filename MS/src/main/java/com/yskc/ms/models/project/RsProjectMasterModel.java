package com.yskc.ms.models.project;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/7/26 9:57
 * @Description:
 */
public class RsProjectMasterModel implements Serializable {

    private Integer year;

    private Integer companyId;

    private Integer mType;

    private Integer userId;

    private Integer rsProjectId;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }
}
