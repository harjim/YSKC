package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.common.enums.FlowInstanceStatusEnum;

import java.util.Date;

/**
 * @DateTime: 2021/9/8 8:13
 * @Description:
 */
@TableName("p_audit_proposal")
public class AuditProposalEntity {
    @TableId
    private Integer id;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer proposalId;
    private Integer moduleId;
    private Integer status;// 0进行中 1通过 2驳回 3激活 4提交 5未提交
    public AuditProposalEntity() {
    }
    public AuditProposalEntity(Date date, Integer msUserId, Integer companyId, Integer proposalId) {
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.proposalId = proposalId;
        this.moduleId = 11;
        this.status = FlowInstanceStatusEnum.SUBMIT.getStatus();
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
    public Integer getProposalId() {
        return proposalId;
    }
    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
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
}
