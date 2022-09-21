package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/6/16 15:16
 * @Description:
 */
public class FlowPatentModel implements Serializable {

    private Integer id;

    private Integer patentPlanId;

    private Integer status;//实例审核状态

    private Integer instanceId;

    private Integer moduleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
