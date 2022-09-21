package com.xxl.job.executor.models.policy;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.policy
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 18:02
 * @Description: 政策来源订阅列表
 */
public class SourceUserModel {
    private Integer sourceId;
    private String dingUserId;
    private Integer userId;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
