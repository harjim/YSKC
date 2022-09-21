package com.yskc.rs.models.project;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-13 10:41
 * @Description: rd列表
 */
public class RdListModel implements Serializable {
    private Integer projectId;
    private Integer rdIndex;
    private String equData;
    private String ecode;
    private String rdHour;
    private String pname;
    private Integer beginYear;

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getEquData() {
        return equData;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getRdHour() {
        return rdHour;
    }

    public void setRdHour(String rdHour) {
        this.rdHour = rdHour;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
