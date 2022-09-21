package com.yskc.rs.models.projectattendance;

import java.util.Date;

/**
 * 人员考勤使用查询
 * @author huronghua
 */
public class UsedQueryModel {
    private Integer companyId;
    private String enumber;
    private Date month;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
