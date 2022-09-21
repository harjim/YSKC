package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-08-12 17:27
 **/
@TableName("flowInstance_curNode")
public class FlowCurNodeEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer instanceId;
    private Integer curNodeId;
    private Integer nodeStatus;
    private Integer flowId;
    private Integer auditLevel;
    private String auditUsers;
    private Date expired;
    private Byte closed;
    private Integer parentNode;

    public FlowCurNodeEntity() {
    }

    public FlowCurNodeEntity(Integer instanceId, Integer curNodeId, Integer nodeStatus) {
        this.instanceId = instanceId;
        this.curNodeId = curNodeId;
        this.nodeStatus = nodeStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getAuditLevel() {
        return auditLevel;
    }

    public void setAuditLevel(Integer auditLevel) {
        this.auditLevel = auditLevel;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Byte getClosed() {
        return closed;
    }

    public void setClosed(Byte closed) {
        this.closed = closed;
    }

    public Integer getParentNode() {
        return parentNode;
    }

    public void setParentNode(Integer parentNode) {
        this.parentNode = parentNode;
    }
}
