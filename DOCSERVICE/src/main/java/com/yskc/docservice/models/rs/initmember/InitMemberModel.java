package com.yskc.docservice.models.rs.initmember;

import java.util.Date;

/**
 * @BelongsProject: doc-service
 * @BelongsPackage: com.yskc.docservice.models.rs.initmember
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-11 16:21
 * @Description: 研发成员model
 */
public class InitMemberModel {

    private Integer projectId;

    private String enumber;

    private String ename;

    private Integer year;

    private Date entryDate;
    private Integer etype;

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

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }
}
