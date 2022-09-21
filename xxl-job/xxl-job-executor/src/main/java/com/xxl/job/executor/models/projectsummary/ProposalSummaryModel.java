package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 17:13
 * @Description: 提案统计
 */
public class ProposalSummaryModel extends BaseSummaryModel {

    private Integer proposalCnt;

    public Integer getProposalCnt() {
        return proposalCnt;
    }

    public void setProposalCnt(Integer proposalCnt) {
        this.proposalCnt = proposalCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setProposalCnt(proposalCnt);
    }
}
