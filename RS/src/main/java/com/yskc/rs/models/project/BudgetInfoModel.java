package com.yskc.rs.models.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/3/4 16:09
 * @Description:
 */
public class BudgetInfoModel implements Serializable {
    private Integer id;

    private Integer projectId;

    private Map<String, BigDecimal> dataMap;

    private Integer year;

    private BigDecimal budget;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Map<String, BigDecimal> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, BigDecimal> dataMap) {
        this.dataMap = dataMap;
    }
}
