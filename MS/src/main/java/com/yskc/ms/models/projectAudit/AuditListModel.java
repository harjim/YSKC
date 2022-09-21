package com.yskc.ms.models.projectAudit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/23 14:23
 * description:项目审核类型列表
 */
public class AuditListModel implements Serializable {

    private Integer id;

    private Integer projectId;

    private Integer companyId;

    private Integer year;

    private Integer moduleId;

    private Boolean pass;

    private Integer nodeId;

    private Integer msLastUpdatorId;

    private String auditor;//审核者

    private Integer order;//当前节点序号

    private Boolean nextNodePerm;//下个节点权限

    private String suggestion;

    private Date createTime;

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    private List<FlowInstanceLogModel> auditLogModels;

    public Boolean getNextNodePerm() {
        return nextNodePerm;
    }

    public void setNextNodePerm(Boolean nextNodePerm) {
        this.nextNodePerm = nextNodePerm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public List<FlowInstanceLogModel> getAuditLogModels() {
        return auditLogModels;
    }

    public void setAuditLogModels(List<FlowInstanceLogModel> auditLogModels) {
        this.auditLogModels = auditLogModels;
    }
}
