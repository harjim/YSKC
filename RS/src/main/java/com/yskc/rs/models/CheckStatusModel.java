package com.yskc.rs.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/5/5 8:32
 * @Description:
 * @author: hsx
 */
public class CheckStatusModel implements Serializable {

    private Integer projectId;

    private Date month;

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

    public CheckStatusModel(Integer projectId, Date month) {
        this.projectId = projectId;
        this.month = month;
    }

    public CheckStatusModel() {
    }
}
