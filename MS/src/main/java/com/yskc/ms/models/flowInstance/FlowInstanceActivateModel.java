package com.yskc.ms.models.flowInstance;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-07 11:02
 * @Description: 流程激活model
 */
public class FlowInstanceActivateModel {

    private List<Integer> instanceIds;

    private String suggestion;

    public List<Integer> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<Integer> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
