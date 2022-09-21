package com.yskc.rs.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-27 10:41
 * @Description: 汇总modal
 */
public class TotalModel implements Serializable {
    private Integer projectId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;
    /**
     * 仪器费用
     */
    private BigDecimal labCost;

    /**
     * 设备费用
     */
    private BigDecimal prodCost;

    private BigDecimal prod;

    private BigDecimal lab;
    private BigDecimal auxiliary;

    private BigDecimal technical;
    private BigDecimal research;

    public BigDecimal getAuxiliary() {
        return auxiliary;
    }

    public void setAuxiliary(BigDecimal auxiliary) {
        this.auxiliary = auxiliary;
    }

    public BigDecimal getTechnical() {
        return technical;
    }

    public void setTechnical(BigDecimal technical) {
        this.technical = technical;
    }

    public BigDecimal getResearch() {
        return research;
    }

    public void setResearch(BigDecimal research) {
        this.research = research;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }

    public BigDecimal getLabCost() {
        return labCost;
    }

    public void setLabCost(BigDecimal labCost) {
        this.labCost = labCost;
    }

    public BigDecimal getProdCost() {
        return prodCost;
    }

    public void setProdCost(BigDecimal prodCost) {
        this.prodCost = prodCost;
    }
}
