package com.yskc.ms.models.projectAudit;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/12/19 8:47
 * description:
 */
public class AuditStatusModel implements Serializable {

    private Integer moduleId;

    private Integer id;//模块id

    private String modeName;//模块名称

    private Integer flowId;

    private Integer seq;

    private Integer status;//实例状态： 0进行中1通过2驳回 null未提交

    private Integer nodeStatus;//节点状态0进行中1通过2驳回3驳回

    private Integer curNodeId;//节点

    private String auditor;//当前处理人

    private Integer instanceId;

    private String nodeName;

    public Integer getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Integer curNodeId) {
        this.curNodeId = curNodeId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
