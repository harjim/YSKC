package com.yskc.ms.models.qualityscore;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/6/9 10:30
 * @Description:查询审核序时账model
 * @author: hsx
 */
public class QueryScoreCollectModel extends PageParams implements Serializable {

    private String companyName;

    private Integer deptId;

    private Integer year;

    private Integer type;

    private String engineerName;

    /**
     * 评分月份
     */
    private Date scoreMonth;

    private Integer isFinal;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public Date getScoreMonth() {
        return scoreMonth;
    }

    public void setScoreMonth(Date scoreMonth) {
        this.scoreMonth = scoreMonth;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }
}
