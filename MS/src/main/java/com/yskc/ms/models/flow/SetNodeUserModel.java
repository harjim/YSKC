package com.yskc.ms.models.flow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/12/3 10:02
 * description:设置流程用户
 */
public class SetNodeUserModel implements Serializable {

    private Integer flowId;

    private Integer nodeId;

    private Integer auditType;//0或签  1 会签

    private Integer type;//0成员1角色2主管

    private List<Integer> userIds;

    private String remark;

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

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
