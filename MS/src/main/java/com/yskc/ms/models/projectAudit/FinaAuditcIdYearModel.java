package com.yskc.ms.models.projectAudit;

import java.io.Serializable;

/**
 * @program: ms
 * @description: companyId and Year
 * @author: cyj
 * @create: 2022-04-24 11:12
 **/
public class FinaAuditcIdYearModel implements Serializable {
    private Integer companyId;
    private Integer year;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
