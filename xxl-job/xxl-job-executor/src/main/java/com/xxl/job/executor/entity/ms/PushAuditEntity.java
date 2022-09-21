package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-16 13:41
 * @Description: 推送审批
 */
@TableName("pushAudit")
public class PushAuditEntity {

    @TableId
    private Integer id;
    private Integer instanceId;
    private Integer nodeId;
    private String msg;
    private Integer status;
    private Date createTime;
    private String sendUserIds;

    public PushAuditEntity(Integer instanceId, Integer nodeId, String msg, Date createTime, Integer status, Set<Integer> sendUser) {
        this.instanceId = instanceId;
        this.nodeId = nodeId;
        this.msg = msg;
        this.status = status;
        this.createTime = createTime;
        this.sendUserIds = sendUser.toString();
    }

    public PushAuditEntity() {
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

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSendUserIds() {
        return sendUserIds;
    }

    public void setSendUserIds(String sendUserIds) {
        this.sendUserIds = sendUserIds;
    }
}
