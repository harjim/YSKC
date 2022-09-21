package com.yskc.rs.models.employeePlan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hck
 * on 2020/11/17 9:44
 * description:
 */
public class PlanTimeModel implements Serializable {

    private Integer companyId;

    private Integer projectId;

    private String enumber;

    private String ename;

    private BigDecimal totalTime;

    private Integer etype;

    private Map<String,RdEmployeePlanModel> dataMap = new HashMap<>();

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Map<String, RdEmployeePlanModel> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, RdEmployeePlanModel> dataMap) {
        this.dataMap = dataMap;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }
}
