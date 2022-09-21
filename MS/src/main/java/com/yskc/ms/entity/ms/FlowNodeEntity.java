package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

import java.util.Date;

/**
 * Created by hck
 * on 2020/11/21 11:37
 * description:流程节点
 */
@TableName("flowNode")
public class FlowNodeEntity {

    @TableId
    private Integer id;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer flowId;
    private String nodeName;
    private Integer type;
    private Integer mode;
    private Integer skip;

    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer nextNode;

    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer prevNode;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer parentId;
    private Integer nodeExpired;
    private Integer nodeNumber;
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public FlowNodeEntity() {
    }

    public FlowNodeEntity(Integer userId, Date date, Integer flowId, String nodeName, Integer type, Integer mode,
                          Integer parentId,Integer nodeExpired,Integer nodeNumber,Integer skip) {
        this.creatorId = userId;
        this.lastUpdatorId = userId;
        this.createTime = date;
        this.lastUpdateTime = date;
        this.flowId = flowId;
        this.nodeName = nodeName;
        this.type = type;
        this.mode = mode;
        this.nextNode = null;
        this.prevNode = null;
        this.parentId = parentId;
        this.nodeExpired = nodeExpired;
        this.nodeNumber = nodeNumber;
        this.skip = skip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getNextNode() {
        return nextNode;
    }

    public void setNextNode(Integer nextNode) {
        this.nextNode = nextNode;
    }

    public Integer getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Integer prevNode) {
        this.prevNode = prevNode;
    }

    public Integer getNodeExpired() {
        return nodeExpired;
    }

    public void setNodeExpired(Integer nodeExpired) {
        this.nodeExpired = nodeExpired;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }
}
