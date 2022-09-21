package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 17:24
 * @Description: 成果统计
 */
public class AchievementSummaryModel extends BaseSummaryModel {
    /**
     * 成果项数（只统计成果数）
     */
    private Integer achievementCnt;

    public Integer getAchievementCnt() {
        return achievementCnt;
    }

    public void setAchievementCnt(Integer achievementCnt) {
        this.achievementCnt = achievementCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setAchievementCnt(achievementCnt);
    }
}
