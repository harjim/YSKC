package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:44
 * @Description: 专利申请统计
 */
public class PatentApplySummaryModel extends BaseSummaryModel {

    private Integer patentApplyCnt;

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setPatentApplyCnt(patentApplyCnt);
    }
}
