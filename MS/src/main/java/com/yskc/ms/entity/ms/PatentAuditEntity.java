package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/8/21 11:30
 * @Description:
 */
@TableName("patent_audit")
public class PatentAuditEntity extends MsBaseEntity {
    @TableId
    private Integer id;

    private Integer companyId;

    private Integer patentPlanId;

    private Integer moduleId;

    private Integer status;

    public PatentAuditEntity() {
    }

    public PatentAuditEntity(Integer companyId, Integer patentPlanId, Integer userId, Date date) {
        this.companyId = companyId;
        this.patentPlanId = patentPlanId;
        this.status = 4;
        this.moduleId = 7;
        create(userId, date);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
