package com.xxl.job.executor.models.flowInstance;

import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-28 08:52
 * @Description: 实例信息
 */
public class FlowInstanceMsgModel {
    private Integer type;
    private Integer status;
    private Set<Integer> userIds;
    private String modeName;
    private String nodeName;
    private String projectName;
    private Integer initiatorId;
    private Integer instanceId;
    private Integer nodeId;
    public Set<Integer> getUserIds() {
        return userIds;
    }

    public FlowInstanceMsgModel() {
    }

    public FlowInstanceMsgModel(Integer status, String modeName, String nodeName, String projectName,
                                Integer initiatorId, Integer instanceId, Integer nodeId,Set<Integer> userIds,Integer type) {
        this.status = status;
        this.modeName = modeName;
        this.nodeName = nodeName;
        this.projectName = projectName;
        this.initiatorId = initiatorId;
        this.instanceId = instanceId;
        this.nodeId = nodeId;
        this.userIds=userIds;
        this.type = type;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
