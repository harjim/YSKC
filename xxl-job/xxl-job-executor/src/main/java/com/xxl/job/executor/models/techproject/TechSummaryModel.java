package com.xxl.job.executor.models.techproject;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.techproject
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 15:19
 * @Description: TechSummary model
 */
public class TechSummaryModel {
    private Integer projectId;
    private Integer sourceProjectId;
    private Integer directionId;
    private Date operationTime;
    private Integer uploadedCount;
    private Integer operationUserId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getUploadedCount() {
        return uploadedCount;
    }

    public void setUploadedCount(Integer uploadedCount) {
        this.uploadedCount = uploadedCount;
    }

    public Integer getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Integer operationUserId) {
        this.operationUserId = operationUserId;
    }
}
