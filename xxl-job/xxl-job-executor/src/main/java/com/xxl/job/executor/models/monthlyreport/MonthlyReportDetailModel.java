package com.xxl.job.executor.models.monthlyreport;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.monthlyreport
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-09 13:58
 * @Description: 月报表汇总
 */
public class MonthlyReportDetailModel {
    private Integer userId;
    private Integer done;
    private Integer reject;
    private Integer commit;
    private String workMonth;
    private Integer moduleId;
    private Integer companyCnt;
    private Integer serviceCnt;
    private Integer companyId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getReject() {
        return reject;
    }

    public void setReject(Integer reject) {
        this.reject = reject;
    }

    public Integer getCommit() {
        return commit;
    }

    public void setCommit(Integer commit) {
        this.commit = commit;
    }

    public String getWorkMonth() {
        return workMonth;
    }

    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getCompanyCnt() {
        return companyCnt;
    }

    public void setCompanyCnt(Integer companyCnt) {
        this.companyCnt = companyCnt;
    }

    public Integer getServiceCnt() {
        return serviceCnt;
    }

    public void setServiceCnt(Integer serviceCnt) {
        this.serviceCnt = serviceCnt;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void addCnt(MonthlyReportDetailModel item) {
        this.commit += item.getCommit() != null ? item.getCommit() : 0;
        this.done += item.getDone() != null ? item.getDone() : 0;
        this.reject += item.getReject() != null ? item.getReject() : 0;
    }

    public void reset() {
        if (this.commit == null) {
            this.commit = 0;
        }
        if (this.done == null) {
            this.done = 0;
        }
        if (this.reject == null) {
            this.reject = 0;
        }
    }
}
