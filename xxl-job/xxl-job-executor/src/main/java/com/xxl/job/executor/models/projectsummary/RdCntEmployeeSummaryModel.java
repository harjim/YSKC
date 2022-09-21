package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-07-11 15:07
 * @Description: 人员统计
 */
public class RdCntEmployeeSummaryModel extends BaseSummaryModel {

    private Integer employeeCount;
    private Integer employeeOpenidCnt;
    private Integer rdEmployeeCount;

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getEmployeeOpenidCnt() {
        return employeeOpenidCnt;
    }

    public void setEmployeeOpenidCnt(Integer employeeOpenidCnt) {
        this.employeeOpenidCnt = employeeOpenidCnt;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
    }
    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setEmployeeCount(employeeCount);
        ps.setRdEmployeeCount(rdEmployeeCount);
        ps.setEmployeeOpenidCnt(employeeOpenidCnt);
    }
}
