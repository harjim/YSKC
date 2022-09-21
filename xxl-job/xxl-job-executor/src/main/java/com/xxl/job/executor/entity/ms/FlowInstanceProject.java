package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.entity.rs.ProjectAuditEntity;

import java.util.Date;

/**
 * @author zdf
 */
@TableName("flowInstance_project")
public class FlowInstanceProject {
    @TableId
    private Integer id;
    private Integer year;
    private Integer companyId;
    private Integer projectId;
    private Integer instanceId;
    private Integer creatorId;
    private Date createTime;
    private Integer moduleId;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;

    public FlowInstanceProject(ProjectAuditEntity item, Date now,Integer moduleId) {
        year = item.getYear();
        companyId = item.getCompanyId();
        projectId = item.getSourceProjectId();
        creatorId = item.getMsLastUpdatorId();
        lastUpdatorId = creatorId;
        createTime = now;
        lastUpdateTime = now;
        this.moduleId = moduleId;
    }

    public FlowInstanceProject() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

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
}
