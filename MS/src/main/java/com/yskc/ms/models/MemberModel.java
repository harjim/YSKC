package com.yskc.ms.models;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 用户成员表通用model,主要用来作查询表涉及的相关用户成员条件
 */
public class MemberModel {
    private Integer[] techIds;
    private Integer[] finIds;
    private Integer ownerId;
    private Integer busId;

    public Integer[] getTechIds() {
        return techIds;
    }

    public void setTechIds(Integer[] techIds) {
        this.techIds = techIds;
    }

    public Integer[] getFinIds() {
        return finIds;
    }

    public void setFinIds(Integer[] finIds) {
        this.finIds = finIds;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public boolean hasMember() {
        return ArrayUtils.isNotEmpty(this.getTechIds()) || ArrayUtils.isNotEmpty(this.getFinIds()) || this.getOwnerId() != null || this.getBusId() != null;
    }
}
