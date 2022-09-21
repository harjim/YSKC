package com.yskc.ms.models.dailyreport;

import com.yskc.ms.models.params.PageParams;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.dailyreport
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 15:17
 * @Description: 查询工作报表
 */
public class QueryDailyReportModel extends PageParams {

    private List<Integer> userIds;

    private Date workDate;

    private String companyName;
    private Date month;

    private Integer deptId;

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
