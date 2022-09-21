package com.yskc.ms.models.projectAudit;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/23 14:18
 * description:审核记录model
 */
public class FlowInstanceLogModel implements Serializable {

    private Integer id;

    private Integer instanceId;

    private String suggestion;

    private Integer status;//审核状态 1通过2驳回3激活

    private Date createTime;

    private Integer msCreatorId;

    private Integer nodeId;

    private String auditor;

    private String nodeName;//节点名称

    private String content;

    private String realName;

    private String companyName;

    private String modeName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getAuditor() {
        return auditor;
    }


    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}
