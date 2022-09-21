package com.yskc.rs.models.data;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.BaseQuery;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * 员工奖金查询条件
 *
 * @author huronghua
 */
public class DataBonusQuery extends PageParams {
    private String ename;
    private Date month;
    private String enumber;
    private Integer etype;
    private Integer companyId;
    private Integer year;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
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
