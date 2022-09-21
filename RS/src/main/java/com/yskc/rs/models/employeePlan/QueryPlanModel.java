package com.yskc.rs.models.employeePlan;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * Created by hck
 * on 2020/11/17 9:47
 * description:
 */
public class QueryPlanModel extends PageParams {
    private Integer projectId;

    private String enumber;

    private String ename;

    private Integer year;

    /**
     * getTotalList请求参数
     */
    private Date month;

    private Date begin;
    private Date end;

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

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

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

    public Date getBegin() {
        if (month != null) {
            return DateUtil.getMonthFirstDay(month);
        }
        if (year != null) {
            return DateUtil.getYearFirstDay(year);
        }
        return begin;
    }

    public Date getEnd() {
        if (month != null) {
            return DateUtil.getMonthLastDay(month);
        }
        if (year != null) {
            return DateUtil.getYearLastDay(year);
        }
        return end;
    }
}
