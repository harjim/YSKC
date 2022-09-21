package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:53
 * @Description: 操作人记录
 */
public class OperationSummaryModel extends BaseSummaryModel {

    private Date operationTime;
    private Integer operationUserId;

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

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setOperationTime(operationTime);
        ps.setOperationUserId(operationUserId);
    }
}
