package com.yskc.rs.models.accountingrdsalary;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.accountingrdsalary
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-20 10:00
 * @Description: 核算研发工资model
 */
public class AccountingRdSalaryModel {
    private Integer projectId;
    private Date month;
    private List<String> enumbers;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<String> getEnumbers() {
        return enumbers;
    }

    public void setEnumbers(List<String> enumbers) {
        this.enumbers = enumbers;
    }
}
