package com.xxl.job.executor.models.flowInstance;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-25 08:41
 * @Description: 过程文档实例model
 */
public class FlowInstanceDocFileModel {
    private Integer instanceId;
    private Integer docFileId;

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
