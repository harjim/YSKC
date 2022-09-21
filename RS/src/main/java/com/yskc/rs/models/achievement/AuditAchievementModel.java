package com.yskc.rs.models.achievement;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.achievement
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 14:45
 * @Description: 审核成果
 */
public class AuditAchievementModel {

    private Integer moduleId;

    private List<Integer> achievementId;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public List<Integer> getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(List<Integer> achievementId) {
        this.achievementId = achievementId;
    }
}
