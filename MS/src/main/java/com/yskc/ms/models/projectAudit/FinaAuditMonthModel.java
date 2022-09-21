package com.yskc.ms.models.projectAudit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: 财务审批每月数据
 * @author: cyj
 * @create: 2022-04-22 16:56
 **/
public class FinaAuditMonthModel  implements Serializable {
    private Integer companyId;
    private Integer year;
    private Date month;
    private BigDecimal amount;
    private Integer auditCnt;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAuditCnt() {
        return auditCnt;
    }

    public void setAuditCnt(Integer auditCnt) {
        this.auditCnt = auditCnt;
    }
}
