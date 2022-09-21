package com.yskc.ms.models.policy;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.policy
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-06 17:43
 * @Description: 政策订阅
 */
public class PolicySubscriptionModel {
    private Integer sourceId;
    private List<Integer> userIds;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
