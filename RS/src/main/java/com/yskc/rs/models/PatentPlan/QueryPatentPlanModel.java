package com.yskc.rs.models.PatentPlan;

import com.yskc.rs.models.params.PageParams;


/**
 * Created by hck
 * on 2020/7/6 11:34
 * description:查询专利立项条件model
 */
public class QueryPatentPlanModel extends PageParams {

    private String patentName;

    private String statusName;

    private Integer status;

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }
}
