package com.yskc.rs.models.voucher;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 */
public class UpdateAccount {
    private Integer projectId;
    private List<Integer> rdType;
    private Date month;
    private String accountNumber;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msLastUpdatorId;

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getRdType() {
        return rdType;
    }

    public void setRdType(List<Integer> rdType) {
        this.rdType = rdType;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
