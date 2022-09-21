package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/26 9:02
 * description:日志
 */
@TableName("flowInstance_log")
public class FlowInstanceLog {

    @TableId
    private Integer id;

    private Integer instanceId;

    private Integer status;//1通过2驳回3激活

    private String suggestion;
    private Integer msCreatorId;

    private Date createTime;

    private Integer nodeId;

    private Integer submiter;

    private Boolean lastNode;

    public FlowInstanceLog(Integer instanceId, Integer status, String suggestion, Integer msCreatorId, Date createTime, Integer nodeId,Integer submiter) {
        this.instanceId = instanceId;
        this.status = status;
        this.suggestion = suggestion;
        this.msCreatorId = msCreatorId;
        this.createTime = createTime;
        this.nodeId = nodeId;
        this.submiter = submiter;
    }

    public FlowInstanceLog() {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getSubmiter() {
        return submiter;
    }

    public void setSubmiter(Integer submiter) {
        this.submiter = submiter;
    }

    public Boolean getLastNode() {
        return lastNode;
    }

    public void setLastNode(Boolean lastNode) {
        this.lastNode = lastNode;
    }
}
