package com.yskc.docservice.entity.rs.project;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.entity.rs.tech.BaseEntity;

import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/6/30 9:30
 * @Description:项目信息（按年）
 */
@TableName("p_year_info")
public class ProjectYearInfoEntity extends BaseEntity {

    @TableId
    private Integer id;

    private Integer year;

    private String masterENumber;

    private BigDecimal budget;

    private Integer projectId;

    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public ProjectYearInfoEntity() {
    }

    public ProjectYearInfoEntity(Integer year, String masterENumber, BigDecimal budget, Integer projectId, Integer companyId) {
        this.year = year;
        this.masterENumber = masterENumber;
        this.budget = budget;
        this.projectId = projectId;
        this.companyId=companyId;
    }

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

    public String getMasterENumber() {
        return masterENumber;
    }

    public void setMasterENumber(String masterENumber) {
        this.masterENumber = masterENumber;
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
}
