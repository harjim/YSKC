package com.yskc.rs.entity.proposal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/9/3 14:49
 * @Description:
 */
@TableName("p_audit_proposal")
public class ProposalAuditEntity implements Serializable {

    @TableId
    private Integer id;
    private Integer moduleId;
    private Integer companyId;
    private Integer proposalId;
    private Integer status;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private List<Integer> proposalIds;

    public ProposalAuditEntity() {

    }

    public ProposalAuditEntity(Date date, Integer msUserId, Integer companyId, Integer proposalId) {
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.proposalId = proposalId;
        this.moduleId = 11;
        this.status = 4;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<Integer> getProposalIds() {
        return proposalIds;
    }

    public void setProposalIds(List<Integer> proposalIds) {
        this.proposalIds = proposalIds;
    }
}
