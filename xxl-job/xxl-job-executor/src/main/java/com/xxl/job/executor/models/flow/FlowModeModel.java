package com.xxl.job.executor.models.flow;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flow
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-15 16:02
 * @Description: 节点流model
 */
public class FlowModeModel {
    private Integer moduleId;
    private Integer nodeId;
    private Integer flowId;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
}
