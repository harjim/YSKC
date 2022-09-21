package com.yskc.ms.models.flow;


/**
 * Created by hck
 * on 2020/11/23 15:35
 * description:
 */
public class FlowNodeModel {

    private Integer id;

    private String nodeName;

    private Integer order;

    private Integer flowId;

    private Integer type;// 0审核，1抄送，2分支
    private Integer auditType;// 审核方式：0或签（任意人员审批即通过），1会签（所有人员审批即通过）
    private Integer nextNode;
    private Integer prevNode;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
