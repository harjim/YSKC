package com.yskc.rs.models.projectyieldconfig;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-03 11:23
 * @Description: 查询试制量配置model
 */
public class QueryYieldConfigModel {
    private Integer projectId;
    private Date month;
    private Integer rdType;
    private Integer type;
    private Integer pDocFileId;

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

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }
}
