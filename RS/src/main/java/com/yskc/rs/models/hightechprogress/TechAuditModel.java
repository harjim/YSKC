package com.yskc.rs.models.hightechprogress;

import java.io.Serializable;

/**
 * @DateTime: 2022/5/27 9:26
 * @Description:
 * @author: hsx
 */
public class TechAuditModel implements Serializable {

    private Integer projectId;
    private Integer type;
    private Integer deliverType;
    private Integer node;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }
}
