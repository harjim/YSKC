package com.yskc.rs.models.projectattendance;

import cn.hutool.core.date.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-07 14:52
 * @Description: 查询所有研发工时model
 */
public class QueryAllRdHourModel extends PageParams {

    private String enumber;

    private Date month;

    private Integer year;

    private Date monthBegin;

    private Date monthEnd;

    private Integer projectId;

    private List<Integer> projectIds;

    private String ename;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonthBegin() {
        if (month != null) {
            return DateUtil.beginOfMonth(month);
        }
        return monthBegin;
    }

    public void setMonthBegin(Date monthBegin) {
        this.monthBegin = monthBegin;
    }

    public Date getMonthEnd() {
        if (month != null) {
            return DateUtil.endOfMonth(month);
        }
        return monthEnd;
    }

    public void setMonthEnd(Date monthEnd) {
        this.monthEnd = monthEnd;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public static QueryAllRdHourModel build(String enumber, Date month, Integer projectId) {
        QueryAllRdHourModel allRdHourModel = new QueryAllRdHourModel();
        allRdHourModel.enumber = enumber;
        allRdHourModel.month = month;
        allRdHourModel.projectId = projectId;
        return allRdHourModel;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }
}
