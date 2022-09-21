package com.yskc.docservice.models.rs.project;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/3/5 11:57
 * @Description:项目研发人员列表
 */
public class ProjectEmployeeModel implements Serializable {

    private Integer projectId;

    private String enumber;

    private String ename;

    private Integer year;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
