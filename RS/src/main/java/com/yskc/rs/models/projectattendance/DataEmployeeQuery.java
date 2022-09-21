package com.yskc.rs.models.projectattendance;

import com.yskc.rs.models.BaseQuery;

import java.util.Date;

/**
 * 添加人员费用查询
 *
 * @author huronghua
 */
public class DataEmployeeQuery extends BaseQuery {
    private String ename;
    private Date month;
    private Integer projectId;
    private Integer minPay;
    private Integer maxPay;
    private Integer etype;
    private String enumber;
    private Integer isMember;
    private Integer companyId;
    private Integer year;
    @Deprecated
    private String deptPath;
    private String rdDeptPath;
    private String deptName;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getMinPay() {
        return minPay;
    }

    public void setMinPay(Integer minPay) {
        this.minPay = minPay;
    }

    public Integer getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Integer maxPay) {
        this.maxPay = maxPay;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
