package com.yskc.rs.models.customerattendance;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.BaseQuery;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.customerattendance
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:43
 * @Description: 查询model
 */
public class QueryCustomerAttendanceModel extends PageParams implements Serializable {
    private Date workDate;
    private String enumber;
    private String ename;
    @Deprecated
    private String deptPath;
    private String deptName;

    private Date startMonth;
    private Date endMonth;

    public Date getStartMonth() {
        if (startMonth != null) {
            return DateUtil.getMonthFirstDay(startMonth);
        }
        return startMonth;
    }

    public void setStartMonth(Date startMonth) {
        this.startMonth = startMonth;
    }

    public Date getEndMonth() {
        if (endMonth != null) {
            return DateUtil.getMonthLastDay(endMonth);
        }
        return endMonth;
    }
    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }

    public Date getWorkDate() {
        if (workDate != null) {
            return DateUtil.getDayBegin(workDate);
        }
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
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

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
