package com.yskc.ms.models.projectAudit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/4/26 8:38
 * @Description:
 * @author: hsx
 */
public class QueryRdFundsModel implements Serializable {

    private Integer companyId;

    private Date month;

    private Integer rdType;

    private List<Integer> children;

    private Integer userId;

    private Integer projectId;

    private Integer rdFeeId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public List<Integer> getChildren() {
        return children;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRdFeeId() {
        return rdFeeId;
    }

    public void setRdFeeId(Integer rdFeeId) {
        this.rdFeeId = rdFeeId;
    }
}
