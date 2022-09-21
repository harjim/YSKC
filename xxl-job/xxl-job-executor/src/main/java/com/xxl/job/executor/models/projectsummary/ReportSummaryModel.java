package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 14:49
 * @Description: 报告汇总
 */
public class ReportSummaryModel extends BaseSummaryModel {

    /**
     * 规划研发数
     */
    private Integer cnt;
    /**
     * 财务核算类型
     */
    private Integer accountType;
    /**
     * 财务规划研发费(万元)
     */
    private Integer rdFee;
    /**
     * 人员总数(录入)
     */
    private Integer employeeAmount;
    /**
     * 营收预测（万元）
     */
    private Integer revenueFcst;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public Integer getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(Integer revenueFcst) {
        this.revenueFcst = revenueFcst;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setCnt(cnt);
        ps.setAccountType(accountType);
        ps.setRdFee(rdFee);
        ps.setEmployeeAmount(employeeAmount);
        ps.setRevenueFcst(revenueFcst);
    }
}
