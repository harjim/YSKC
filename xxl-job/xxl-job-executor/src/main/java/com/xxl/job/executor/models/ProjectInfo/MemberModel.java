package com.xxl.job.executor.models.ProjectInfo;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/7/22 17:24
 * @Description:项目成员||设备||文档审核||专利统计
 */
public class MemberModel implements Serializable {

    private Integer year;

    private Integer projectId;

    private Integer companyId;

    private Integer countData;

    private Integer status;//审核状态

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

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

    public Integer getCountData() {
        return countData;
    }

    public void setCountData(Integer countData) {
        this.countData = countData;
    }
}
