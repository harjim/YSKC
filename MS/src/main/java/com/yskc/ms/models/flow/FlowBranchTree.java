package com.yskc.ms.models.flow;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/16 8:40
 * description:分支树
 */
public class FlowBranchTree {

    private Integer branchId;//分支id

    private String condition;

    private Integer seq;

    private Integer nodeId;


    private Integer flowId;//新流程id

    private List<FlowNodeTreeModel> newFlow;//新流程

    private String newFlowName;//新流程name

    private String remark;//新流程备注

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNewFlowName() {
        return newFlowName;
    }

    public void setNewFlowName(String newFlowName) {
        this.newFlowName = newFlowName;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }


    public List<FlowNodeTreeModel> getNewFlow() {
        return newFlow;
    }

    public void setNewFlow(List<FlowNodeTreeModel> newFlow) {
        this.newFlow = newFlow;
    }
}
