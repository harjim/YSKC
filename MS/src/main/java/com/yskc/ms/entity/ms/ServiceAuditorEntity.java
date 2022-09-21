package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-08-12 16:24
 **/
@TableName("service_apply_auditor")
public class ServiceAuditorEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer serviceApplyId;
    private Integer techManagerId;
    private Integer techDirectorId;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private Integer instanceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceApplyId() {
        return serviceApplyId;
    }

    public void setServiceApplyId(Integer serviceApplyId) {
        this.serviceApplyId = serviceApplyId;
    }

    public Integer getTechManagerId() {
        return techManagerId;
    }

    public void setTechManagerId(Integer techManagerId) {
        this.techManagerId = techManagerId;
    }

    public Integer getTechDirectorId() {
        return techDirectorId;
    }

    public void setTechDirectorId(Integer techDirectorId) {
        this.techDirectorId = techDirectorId;
    }

    public Integer getFinaManagerId() {
        return finaManagerId;
    }

    public void setFinaManagerId(Integer finaManagerId) {
        this.finaManagerId = finaManagerId;
    }

    public Integer getFinaDirectorId() {
        return finaDirectorId;
    }

    public void setFinaDirectorId(Integer finaDirectorId) {
        this.finaDirectorId = finaDirectorId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }
}
