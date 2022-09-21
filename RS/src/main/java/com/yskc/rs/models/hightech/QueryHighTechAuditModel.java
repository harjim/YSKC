package com.yskc.rs.models.hightech;

import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: rs
 * @description: 高新进度审核model
 * @author: cyj
 * @create: 2022-05-20 15:03
 **/
public class QueryHighTechAuditModel implements Serializable {
    private Integer companyId;
    private Integer projectId;
    private Integer year;
    private Integer type;
    private Integer deliverType;
    private Integer node;
    private Integer status;
    private Date month;
    private String auditUser;
    private String suggestion;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
