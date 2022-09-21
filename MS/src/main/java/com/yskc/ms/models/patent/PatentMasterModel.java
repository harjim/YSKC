package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * @DateTime: 2022/2/22 10:20
 * @Description: 专利批量设置代理人MODEL
 * @author: hsx
 */
public class PatentMasterModel implements Serializable {

    private Integer instanceId;

    private Integer masterId;

    private Integer oldUserId;

    // 0：技术交底，1：交底审批，2：代理人分配，3：定稿，4：受理，5:受理缴费，6：授权，7授权缴费
    private Integer nodeNumber;

    private Integer id;

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Integer getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(Integer oldUserId) {
        this.oldUserId = oldUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
