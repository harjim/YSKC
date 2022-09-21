package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:46
 * @Description: 知识产权统计
 */
public class PatentSummaryModel extends BaseSummaryModel {

    private Integer patentCnt;

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setPatentCnt(patentCnt);
    }
}
