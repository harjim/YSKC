package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:22
 * @Description: 文档统计
 */
public class DocFileSummaryModel extends BaseSummaryModel {

    private Integer docFileCount;
    private Integer reportCnt;

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Integer getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(Integer reportCnt) {
        this.reportCnt = reportCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setDocFileCount(docFileCount);
        ps.setReportCnt(reportCnt);
    }
}
