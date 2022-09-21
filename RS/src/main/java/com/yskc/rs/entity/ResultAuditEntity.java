package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/9/3 14:49
 * @Description:
 */
@TableName("p_audit_result")
public class ResultAuditEntity implements Serializable {

    @TableId
    private Integer id;
    private Integer moduleId;
    private Integer companyId;
    private Integer status;
    private Integer documentId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private List<Integer> documentIds;

    public ResultAuditEntity(Date date, Integer msUserId, Integer companyId, Integer documentId) {
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.documentId = documentId;
        this.moduleId = 12;
        this.status = 4;
    }

    public ResultAuditEntity() {

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
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

    public List<Integer> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<Integer> documentIds) {
        this.documentIds = documentIds;
    }
}
