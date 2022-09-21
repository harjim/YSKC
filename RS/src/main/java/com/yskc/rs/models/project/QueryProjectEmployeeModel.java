package com.yskc.rs.models.project;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 08:55
 * @Description: 查询项目人员
 */
public class QueryProjectEmployeeModel implements Serializable {

    private Integer year;

    private Integer projectId;

    private String ename;

    private Boolean allEmployee;

    private Boolean rdAndCommittee;//true 是评审委员和研发人员下拉

    public Boolean getRdAndCommittee() {
        return rdAndCommittee;
    }

    public void setRdAndCommittee(Boolean rdAndCommittee) {
        this.rdAndCommittee = rdAndCommittee;
    }

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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Boolean getAllEmployee() {
        if (allEmployee == null) {
            return false;
        }
        return allEmployee;
    }

    public void setAllEmployee(Boolean allEmployee) {
        this.allEmployee = allEmployee;
    }
}

