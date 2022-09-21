package com.yskc.rs.models.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/26 14:10
 * description:
 */
public class SetRdAmountModel implements Serializable {

    private Integer projectId;

    private BigDecimal amount;

    private BigDecimal otherAmount;

    private List<Integer> pRdIds;

    private Date month;

    private Integer type;

    private List<Integer> rdTypes;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Integer> getpRdIds() {
        return pRdIds;
    }

    public void setpRdIds(List<Integer> pRdIds) {
        this.pRdIds = pRdIds;
    }

    public List<Integer> getRdTypes() {
        return rdTypes;
    }

    public void setRdTypes(List<Integer> rdTypes) {
        this.rdTypes = rdTypes;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }
}
