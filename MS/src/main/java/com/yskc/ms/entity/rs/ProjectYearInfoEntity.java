package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.rs.models.RsBaseEntity;

import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/6/30 9:30
 * @Description:项目信息（按年）
 */
@TableName("p_year_info")
public class ProjectYearInfoEntity extends RsBaseEntity {

    @TableId
    private Integer id;

    private Integer year;

    private String masterENumber;

    private BigDecimal budget;

    private Integer projectId;

    public ProjectYearInfoEntity() {
    }

    public ProjectYearInfoEntity(Integer year, String masterENumber, BigDecimal budget, Integer projectId) {
        this.year = year;
        this.masterENumber = masterENumber;
        this.budget = budget;
        this.projectId = projectId;
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
