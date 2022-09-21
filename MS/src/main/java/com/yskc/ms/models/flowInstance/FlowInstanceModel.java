package com.yskc.ms.models.flowInstance;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FlowInstanceModel {

    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdateTime;
    private Integer curNodeId;
    private Integer flowId;
    private Integer status;//实例状态：0进行中1通过2驳回3激活
    private Integer moduleId;
    private Integer nodeStatus;//节点状态 0进行中1通过2驳回3激活
    private Integer lastSubmiter;
    private String modeName;
    private String realName;
    private String companyName;
    private String content;
    private String submiter;
    private Integer type;
    private Integer companyId;
    private Boolean revokable;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public FlowInstanceModel() {
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setCurNodeId(Integer curNodeId) {
        this.curNodeId = curNodeId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public void setLastSubmiter(Integer lastSubmiter) {
        this.lastSubmiter = lastSubmiter;
    }

    public Integer getId() {
        return id;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Integer getCurNodeId() {
        return curNodeId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public Integer getNodeStatus() {
        return nodeStatus;
    }

    public Integer getLastSubmiter() {
        return lastSubmiter;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modelName) {
        this.modeName = modelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubmiter() {
        return submiter;
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean getRevokable() {
        return revokable;
    }

    public void setRevokable(Boolean revokable) {
        this.revokable = revokable;
    }
}
