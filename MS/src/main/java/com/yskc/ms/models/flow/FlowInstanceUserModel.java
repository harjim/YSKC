package com.yskc.ms.models.flow;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/12/19 10:08
 * description:实例用户
 */
public class FlowInstanceUserModel implements Serializable {

    private Integer userId;

    private String userName;

    private Integer nodeId;

    private Integer status;

    private Integer instanceId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }
}
