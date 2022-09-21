package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/6/8 11:36
 * @Description:专利交底书审核
 **/
@TableName("p_audit_patent")
public class AuditPatentEntity{

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer patentPlanId;

    private Integer moduleId;

    private Integer status;

    private String suggestion;

    private Integer msCreatorId;

    private Integer msLastUpdatorId;

    private Date createTime;

    private Date lastUpdateTime;

    public AuditPatentEntity() {
    }

    public AuditPatentEntity(Integer companyId, Integer patentPlanId,  Integer msUserId,  Date date) {
        this.companyId = companyId;
        this.patentPlanId = patentPlanId;
        this.moduleId = 7;
        this.status = 4;
        this.suggestion = "";
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.createTime = date;
        this.lastUpdateTime = date;
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

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
