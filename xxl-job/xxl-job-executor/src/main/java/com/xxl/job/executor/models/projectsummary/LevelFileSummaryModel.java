package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 16:39
 * @Description: 多层级文件统计
 */
public class LevelFileSummaryModel extends BaseSummaryModel {

    private Integer levelFileCnt;

    public Integer getLevelFileCnt() {
        return levelFileCnt;
    }

    public void setLevelFileCnt(Integer levelFileCnt) {
        this.levelFileCnt = levelFileCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setLevelFileCnt(levelFileCnt);
    }
}
