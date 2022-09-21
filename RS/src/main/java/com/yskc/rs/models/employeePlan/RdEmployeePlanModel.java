package com.yskc.rs.models.employeePlan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/16 17:30
 * description:
 */
public class RdEmployeePlanModel implements Serializable {

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private String enumber;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;

    private BigDecimal planTime;

    private String ename;

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

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public BigDecimal getBitPlanTime(int bit) {
        return planTime != null ? planTime.setScale(bit, BigDecimal.ROUND_DOWN) : null;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
