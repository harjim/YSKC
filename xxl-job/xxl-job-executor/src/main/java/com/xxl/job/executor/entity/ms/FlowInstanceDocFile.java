package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.models.flowInstance.DocFileAuditInfoModel;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-25 08:28
 * @Description: 过程文档实例
 */
@TableName("flowInstance_docFile")
public class FlowInstanceDocFile {
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer rsProjectId;
    private Integer docFileId;
    private Integer instanceId;
    private Integer moduleId;
    private Integer creatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;

    public FlowInstanceDocFile(DocFileAuditInfoModel item, Date now, Integer moduleId) {
        this.companyId = item.getCompanyId();
        this.rsProjectId = item.getProjectId();
        this.docFileId = item.getDocFileId();
        this.moduleId = moduleId;
        createTime = now;
        lastUpdateTime = now;
        creatorId = item.getMsLastUpdatorId();
        lastUpdatorId = item.getMsLastUpdatorId();
    }

    public FlowInstanceDocFile() {
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

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }
}
