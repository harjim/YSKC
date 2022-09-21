package com.xxl.job.executor.models.hightechprogress;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/5/21 11:17
 * @Description:
 * @author: hsx
 */
public class ProjectDeliverModel implements Serializable {

    private Integer companyId;

    private Integer projectId;

    private Date month;

    private Integer deliverType;

    private Integer type;

    private Integer node;

    private Integer status;

    private Integer year;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
