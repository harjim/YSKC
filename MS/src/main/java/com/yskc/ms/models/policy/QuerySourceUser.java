package com.yskc.ms.models.policy;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.policy
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 14:09
 * @Description: 查询政策关注人员
 */
public class QuerySourceUser extends PageParams {
    private String realName;

    private String fullPath;

    private Integer sourceId;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
