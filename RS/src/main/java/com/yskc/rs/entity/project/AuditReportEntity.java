package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/29 17:49
 * @Description:项目审核
 */
@TableName("p_audit_report")
public class AuditReportEntity {
    @TableId
    private Integer id;

    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private Integer companyId;
    private Integer projectId;
    private Integer moduleId;
    private Integer status;// 0进行中 1通过 2驳回 3激活 4提交 5未提交

    public AuditReportEntity() {
    }

    public AuditReportEntity(Date date, Integer msUserId, Integer companyId, Integer projectId) {
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.projectId = projectId;
        this.moduleId = 4;
        this.status = 4;
    }

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
}
