package com.yskc.ms.models.flow;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/3/17 9:36
 * @Description:
 * @author: hsx
 */
public class SetFlowMasterModel implements Serializable {

    private List<Integer> instanceIds;

    private List<Integer> userIds;

    private Integer nodeId;

    public List<Integer> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<Integer> instanceIds) {
        this.instanceIds = instanceIds;
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
}
