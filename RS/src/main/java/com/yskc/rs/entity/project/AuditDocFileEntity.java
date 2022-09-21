package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2021/1/25 8:43
 * description:过程文件审核
 */
@TableName("p_audit_docFile")
public class AuditDocFileEntity implements Serializable {

    @TableId
    private Integer id;

    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer projectId;
    private Integer docFileId;
    private Integer moduleId;
    private Integer status;// 0进行中 1通过 2驳回 3激活 4提交 5未提交
    private String suggestion;

    public AuditDocFileEntity() {
    }

    public AuditDocFileEntity(Date date,
                              Integer msUserId, Integer companyId, Integer projectId, Integer docFileId, Integer moduleId, Integer status) {
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.companyId = companyId;
        this.projectId = projectId;
        this.docFileId = docFileId;
        this.moduleId = moduleId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
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


}
