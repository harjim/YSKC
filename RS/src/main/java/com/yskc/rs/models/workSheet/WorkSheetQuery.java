package com.yskc.rs.models.workSheet;

import com.yskc.common.utils.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 工单查询
 *
 * @author huronghua
 */
public class WorkSheetQuery {
    private Date workDate;
    private Integer projectId;
    private Date month;
    private List<Integer> types;
    private Integer year;

    public Date getMonth() {
        if (workDate != null) {
            return DateUtil.getMonthFirstDay(workDate);
        }
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
