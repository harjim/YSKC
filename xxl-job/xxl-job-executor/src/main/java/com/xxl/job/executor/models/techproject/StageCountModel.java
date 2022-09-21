package com.xxl.job.executor.models.techproject;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.techproject
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 16:53
 * @Description: stage统计
 */
public class StageCountModel {

    private Integer stageCount;

    private Integer uploadCount;

    private Integer directionId;

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

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }
}
