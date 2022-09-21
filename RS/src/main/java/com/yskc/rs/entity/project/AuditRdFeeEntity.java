package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/4/28 8:44
 * @Description:
 * @author: hsx
 */

@TableName("p_audit_rdFee")
public class AuditRdFeeEntity implements Serializable {

    @TableId
    private Integer id;

    private Integer companyId;

    private Date month;

    private Integer projectId;

    private Integer rdType;

    private Integer moduleId;

    private Integer status;

    private Integer msCreatorId;

    private Integer msLastUpdatorId;

    private Date lastUpdateTime;

    private Date createTime;

    private String suggestion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
