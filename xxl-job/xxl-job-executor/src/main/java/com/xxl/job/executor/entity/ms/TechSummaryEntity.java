package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.models.techproject.CustomerIdYearModel;
import com.xxl.job.executor.models.techproject.StageCountModel;
import com.xxl.job.executor.models.techproject.TechSummaryModel;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 15:08
 * @Description: tech_summary表
 */
@TableName("tech_summary")
public class TechSummaryEntity {

    @TableId
    private Integer id;
    private Integer projectId;
    private Integer customerId;
    private Integer year;
    private Date createTime;
    private Date updateTime;
    private Date operationTime;
    private Integer operationUserId;
    private Integer stageCount;
    private Integer uploadCount;
    private Integer uploadedCount;

    public static TechSummaryEntity build(TechSummaryModel item, CustomerIdYearModel cIdYear, StageCountModel stageCount, Date now) {
        TechSummaryEntity entity = new TechSummaryEntity();
        entity.createTime = now;
        entity.updateTime = now;
        entity.projectId = item.getSourceProjectId();
        entity.customerId = cIdYear.getCustomerId();
        entity.year = cIdYear.getYear();
        entity.operationTime = item.getOperationTime();
        entity.operationUserId = item.getOperationUserId();
        entity.stageCount = stageCount.getStageCount();
        entity.uploadCount = stageCount.getUploadCount();
        entity.uploadedCount = item.getUploadedCount();
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Integer operationUserId) {
        this.operationUserId = operationUserId;
    }

    public Integer getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    public Integer getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(Integer uploadCount) {
        this.uploadCount = uploadCount;
    }

    public Integer getUploadedCount() {
        return uploadedCount;
    }

    public void setUploadedCount(Integer uploadedCount) {
        this.uploadedCount = uploadedCount;
    }
}
