package com.yskc.rs.models.projectfinaschedule;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectfinaschedule
 * @Author: zhangdingfu
 * @CreateTime: 2022-02-24 14:20
 * @Description: 查询财务排期（试验试制实际工时）
 */
public class QueryProjectFinaScheduleModel extends PageParams {

    private Integer projectId;

    private Date month;

    private String deptName;

    private Integer year;
    private Date begin;
    private Date end;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getBegin() {
        return null != begin ? begin : DateUtil.getYearFirstDay(year);
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return null != end ? end : DateUtil.getYearLastDay(year);
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
