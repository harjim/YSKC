package com.yskc.rs.models.projectattendance;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * 按条件添加
 *
 * @author huronghua
 */
public class QueryBatchEmployee extends PageParams {
    private Date month;
    private Integer projectId;
    private String enumber;
    private String ename;
    private Integer etype;
    private Boolean all;

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
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

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

}
