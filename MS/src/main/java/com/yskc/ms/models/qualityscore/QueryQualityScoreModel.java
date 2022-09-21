package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/5/13 16:11
 * @Description:
 * @author: hsx
 */
public class QueryQualityScoreModel implements Serializable {

    private Integer companyId;

    private Integer type;

    private Integer rsProjectId;

    private Integer year;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

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

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
