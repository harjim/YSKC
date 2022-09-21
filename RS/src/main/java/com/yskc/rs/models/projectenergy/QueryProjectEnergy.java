package com.yskc.rs.models.projectenergy;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-16 19:05
 * @Description: 能源查询mdoel
 */
public class QueryProjectEnergy extends PageParams {
    private Integer projectId;
    private Integer etype;
    private String ename;
    private Date month;
    private Date monthBegin;
    private Date monthEnd;
    private String deptName;
    private Integer type;
    private String remark;
    private Integer year;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getMonthBegin() {
        return DateUtil.getMonthFirstDay(month);
    }

    public void setMonthBegin(Date monthBegin) {
        this.monthBegin = monthBegin;
    }

    public Date getMonthEnd() {
        return DateUtil.getMonthLastDay(month);
    }

    public void setMonthEnd(Date monthEnd) {
        this.monthEnd = monthEnd;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
