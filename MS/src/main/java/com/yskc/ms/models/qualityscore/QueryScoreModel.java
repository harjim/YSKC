package com.yskc.ms.models.qualityscore;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/6/9 11:30
 * @Description:
 * @author: hsx
 */
public class QueryScoreModel extends PageParams implements Serializable {

    private Integer companyId;

    private Integer type;

    private Integer year;

    private String month;

    private Integer engineerId;

    private Integer isFinal;

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }
}
