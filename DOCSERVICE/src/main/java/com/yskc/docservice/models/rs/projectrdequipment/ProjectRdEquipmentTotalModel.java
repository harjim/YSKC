package com.yskc.docservice.models.rs.projectrdequipment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:48
 * @Description: 项目设备折旧费用总计
 */
public class ProjectRdEquipmentTotalModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal lab;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal prod;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal assets;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal power;
    private Date month;
    private Integer projectId;

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public void setAssets(BigDecimal assets) {
        this.assets = assets;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
