package com.yskc.rs.models.project;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/3/31 10:56
 * @Description:
 * @author: hsx
 */
public class SaveBudgetModel implements Serializable {

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private BigDecimal value;

    private Integer year;

    private String key;

    private Integer type;

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
