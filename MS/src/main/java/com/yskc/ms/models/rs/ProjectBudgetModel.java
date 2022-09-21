package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/3 9:25
 * @Description:项目预算model
 */
public class ProjectBudgetModel implements Serializable {

    private Integer projectId;

    private Integer rdType;

    private Date month;

    private BigDecimal rdFunds;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
