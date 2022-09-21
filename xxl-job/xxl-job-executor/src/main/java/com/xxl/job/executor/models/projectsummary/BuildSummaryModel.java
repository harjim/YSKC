package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:48
 * @Description: 建设事项统计
 */
public class BuildSummaryModel  extends BaseSummaryModel {

    private Integer buildCount;

    public Integer getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }


    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setBuildCnt(buildCount);
    }
}
