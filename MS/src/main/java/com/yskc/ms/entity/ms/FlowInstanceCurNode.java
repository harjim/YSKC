package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-08-13 08:55
 * @Description: 流程节点
 */
public class FlowInstanceCurNode extends MsBaseEntity {

    @TableId
    private Integer id;
    private Integer instanceId;
    private Integer curNodeId;
    private Integer nodeStatus;
    private Integer flowId;
    private Integer auditLevel = 0;
    private String auditUsers;
    private Date expired;
    private Date createTime;
    private Date lastupdateTime;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Integer parentNode;
    /**
     * 关闭
     */
    private Boolean closed = false;

    public FlowInstanceCurNode(Integer instanceId, Integer nodeStatus,Integer curNodeId) {
        this.instanceId = instanceId;
        this.nodeStatus = nodeStatus;
        this.curNodeId = curNodeId;
    }

    public FlowInstanceCurNode() {
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

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Date lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    @Override
    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getParentNode() {
        return parentNode;
    }

    public void setParentNode(Integer parentNode) {
        this.parentNode = parentNode;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
