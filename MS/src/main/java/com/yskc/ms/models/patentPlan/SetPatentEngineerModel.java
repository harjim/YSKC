package com.yskc.ms.models.patentPlan;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patentPlan
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-28 16:33
 * @Description: 分配专利申请工程师model
 */
public class SetPatentEngineerModel {

    private Integer engineerId;

    private List<Integer> ids;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
