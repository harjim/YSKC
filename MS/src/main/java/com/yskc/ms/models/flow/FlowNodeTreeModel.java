package com.yskc.ms.models.flow;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/16 8:36
 * description:流程节点
 */
public class FlowNodeTreeModel {

    private Integer flowId;

    private Integer nodeId;//节点id


    private Integer type;//1审核，2抄送，3分支

    private List<FlowBranchTree> list;//分支集合

    private String nodeName;//当前节点名称

    private Integer auditType;//或签  会签

    private Integer prevNode;

    private Integer nextNode;

    private List<NodeAuditorModel> userList;//指定审核成员列表

    private Integer userType;//审核人员类型 0人员1角色2主管

    private Integer nodeExpired;//期限

    private Integer nodeNumber;//编号

    private Integer skip; //是否重复审批自动跳过

    private String remark;//备注

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<NodeAuditorModel> getUserList() {
        return userList;
    }

    public void setUserList(List<NodeAuditorModel> userList) {
        this.userList = userList;
    }

    public Integer getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Integer prevNode) {
        this.prevNode = prevNode;
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

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

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

    public List<FlowBranchTree> getList() {
        return list;
    }

    public void setList(List<FlowBranchTree> list) {
        this.list = list;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
