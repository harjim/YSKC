package com.yskc.rs.models.reportform;

import java.io.Serializable;
/**
 * @program: rs
 * @description: 指标总模型
 * @author: cyj
 * @create: 2022-01-19 16:20
 **/
public class IndexModel implements Serializable {
    private TotalIndexModel totalIndex = new TotalIndexModel();
    private RdFundsIndexModel rdFundsIndex = new RdFundsIndexModel();
    private RdManageIndexModel rdManageIndex = new RdManageIndexModel();
    private AnalyizeIndexModel proportIndex = new AnalyizeIndexModel();
    private AnalyizeIndexModel costIndex = new AnalyizeIndexModel();
    private TechIndexModel techIndex = new TechIndexModel();
    private CostModel cost = new CostModel();

    public TotalIndexModel getTotalIndex() {
        return totalIndex;
    }

    public void setTotalIndex(TotalIndexModel totalIndex) {
        this.totalIndex = totalIndex;
    }

    public RdFundsIndexModel getRdFundsIndex() {
        return rdFundsIndex;
    }

    public void setRdFundsIndex(RdFundsIndexModel rdFundsIndex) {
        this.rdFundsIndex = rdFundsIndex;
    }

    public RdManageIndexModel getRdManageIndex() {
        return rdManageIndex;
    }

    public void setRdManageIndex(RdManageIndexModel rdManageIndex) {
        this.rdManageIndex = rdManageIndex;
    }

    public AnalyizeIndexModel getProportIndex() {
        return proportIndex;
    }

    public void setProportIndex(AnalyizeIndexModel proportIndex) {
        this.proportIndex = proportIndex;
    }

    public AnalyizeIndexModel getCostIndex() {
        return costIndex;
    }

    public void setCostIndex(AnalyizeIndexModel costIndex) {
        this.costIndex = costIndex;
    }

    public TechIndexModel getTechIndex() {
        return techIndex;
    }

    public void setTechIndex(TechIndexModel techIndex) {
        this.techIndex = techIndex;
    }

    public CostModel getCost() {
        return cost;
    }

    public void setCost(CostModel cost) {
        this.cost = cost;
    }

}
