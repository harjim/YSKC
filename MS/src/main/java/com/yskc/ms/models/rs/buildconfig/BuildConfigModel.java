package com.yskc.ms.models.rs.buildconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.rs.EmployeeSelectModel;


import java.io.Serializable;
import java.util.Date;

/**
 * @program: rs
 * @description:
 * @author: cyj
 * @create: 2022-01-05 08:49
 **/
public class BuildConfigModel implements Serializable{
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer year;
    private Integer type;
    private String madeDept;
    private EmployeeSelectModel maker;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date issueDate;
    private String version;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;
    private EmployeeSelectModel approval;
    private EmployeeSelectModel auditor;
    private EmployeeSelectModel rdDeptMaster;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMadeDept() {
        return madeDept;
    }

    public void setMadeDept(String madeDept) {
        this.madeDept = madeDept;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public EmployeeSelectModel getMaker() {
        return maker;
    }

    public void setMaker(EmployeeSelectModel maker) {
        this.maker = maker;
    }

    public EmployeeSelectModel getApproval() {
        return approval;
    }

    public void setApproval(EmployeeSelectModel approval) {
        this.approval = approval;
    }

    public EmployeeSelectModel getAuditor() {
        return auditor;
    }

    public void setAuditor(EmployeeSelectModel auditor) {
        this.auditor = auditor;
    }

    public EmployeeSelectModel getRdDeptMaster() {
        return rdDeptMaster;
    }

    public void setRdDeptMaster(EmployeeSelectModel rdDeptMaster) {
        this.rdDeptMaster = rdDeptMaster;
    }
}

