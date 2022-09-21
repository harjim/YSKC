package com.xxl.job.executor.models.flow;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flow
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-23 11:01
 * @Description: 分支流程
 */
public class FlowBranchModel {

    private Integer flowId;

    private Integer nodeId;

    private Integer nextFlowId;

    private Integer nextNode;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getNextFlowId() {
        return nextFlowId;
    }

    public void setNextFlowId(Integer nextFlowId) {
        this.nextFlowId = nextFlowId;
    }

    public Integer getNextNode() {
        return nextNode;
    }

    public void setNextNode(Integer nextNode) {
        this.nextNode = nextNode;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
