package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @author zdf
 */
@TableName("flowInstance_user")
public class FlowInstanceUser {

    @TableId
    private Integer id;
    private Integer instanceId;
    private Integer userId;
    private Integer nodeId;
    private Integer creatorId;
    private Date createTime;
    private Boolean status;

    public FlowInstanceUser() {
    }

    public FlowInstanceUser(Integer nodeId, Integer dataId, Date now, Integer instanceId, Integer creatorId) {
        this.nodeId = nodeId;
        this.userId = dataId;
        this.createTime = now;
        this.instanceId = instanceId;
        this.creatorId = creatorId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
