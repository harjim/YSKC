package com.yskc.ms.models;

import java.io.Serializable;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-08 10:53
 **/
public class FormAuditModel implements Serializable {
    private Integer nodeNumber;
    private Boolean pass;//true 通过 false 驳回
    private String suggestion;

    private Integer instanceId;

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }
}
