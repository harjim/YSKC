package com.yskc.ms.models.flowInstance;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-16 17:24
 * @Description: rd实例
 */
public class RdInstanceModel {
    private Integer companyId;
    private Integer instanceId;
    private Integer rsProjectId;
    private Integer dataId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }
}
