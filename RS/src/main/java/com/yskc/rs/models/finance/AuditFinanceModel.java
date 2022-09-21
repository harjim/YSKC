package com.yskc.rs.models.finance;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/4/28 8:36
 * @Description: 财务提交审核model
 * @author: hsx
 */
public class AuditFinanceModel implements Serializable {

    private Integer projectId;

    private Date month;

    private Integer rdType;

    private Integer companyId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
