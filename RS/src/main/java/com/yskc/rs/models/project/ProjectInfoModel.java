package com.yskc.rs.models.project;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-02 17:03
 * @Description: 项目基础信息
 */
public class ProjectInfoModel implements Serializable {
    private Integer id;
    private String rdTitle;
    private String workshop;
    private String pname;
    private String rdNumber;

    private Integer highTechProjectId;

    public Integer getHighTechProjectId() {
        return highTechProjectId;
    }

    public void setHighTechProjectId(Integer highTechProjectId) {
        this.highTechProjectId = highTechProjectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }
}
