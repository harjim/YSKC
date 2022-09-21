package com.yskc.ms.models.innovationproject;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.innovationproject
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-26 11:10
 * @Description: 批量设置负责人及成员
 */
public class BatchMasterStaffModel {

    private List<Integer> innovationIds;

    @NotNull(message = "负责人不能为空")
    private Integer masterId;

    private List<Integer> userIds;

    public List<Integer> getInnovationIds() {
        return innovationIds;
    }

    public void setInnovationIds(List<Integer> innovationIds) {
        this.innovationIds = innovationIds;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
