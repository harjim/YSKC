package com.yskc.rs.models.projectrdemployee;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:21
 * @Description: 项目人员费用查询model
 */
public class QueryProjectRdEmployeeModel extends PageParams implements Serializable {
    private String enumber;
    private String ename;
    private Integer etype;
    private Integer projectId;
    private Date month;
    private Integer year;
    private String deptName;
    private Integer pDocFileId;//过程文档id
    /**
     * companyId+type高新进度查询明细条件
     */
    private Integer companyId;
    private Integer type = 0;

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonthLastDay() {
        return DateUtil.getMonthLastDay(month);
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getType() {
        return type;
    }
}
