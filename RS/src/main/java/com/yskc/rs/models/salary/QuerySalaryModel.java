package com.yskc.rs.models.salary;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.salary
 * @Author: wangxing
 * @CreateTime: 2019-09-04 16:22
 * @Description: 查询条件
 */
public class QuerySalaryModel {
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
