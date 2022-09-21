package com.yskc.ms.models.xxljob;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.xxljob
 * @Author: zhangdingfu
 * @CreateTime: 2021-12-17 12:12
 * @Description: xxljob配置
 */
public class XxlJobConfig {

    private String url;

    private Integer rsOperationLogJobId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRsOperationLogJobId() {
        return rsOperationLogJobId;
    }

    public void setRsOperationLogJobId(Integer rsOperationLogJobId) {
        this.rsOperationLogJobId = rsOperationLogJobId;
    }
}
