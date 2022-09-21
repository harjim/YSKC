package com.xxl.job.executor.models.audit;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.audit
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-20 14:10
 * @Description: 项目审批统计
 */
public class ProjectAuditCntModel {

    private Integer projectId;

    private Integer companyId;

    private Integer year;

    private Integer doneCnt;

    private Integer ongoingCnt;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDoneCnt() {
        return doneCnt;
    }

    public void setDoneCnt(Integer doneCnt) {
        this.doneCnt = doneCnt;
    }

    public Integer getOngoingCnt() {
        return ongoingCnt;
    }

    public void setOngoingCnt(Integer ongoingCnt) {
        this.ongoingCnt = ongoingCnt;
    }

}
