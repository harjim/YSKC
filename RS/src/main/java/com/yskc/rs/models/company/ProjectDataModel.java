package com.yskc.rs.models.company;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/15 11:23
 * @Description:首页项目预算占比统计信息
 */
public class ProjectDataModel implements Serializable {

    private Integer id;//项目id

    private String rdTitle;

    private BigDecimal budget;

    private String pname;

    private BigDecimal rdFunds;//归集费用

    private BigDecimal percent;

    private List<ProjectDataModel> childs;

    private BigDecimal totalBudget;//总预算

    private Boolean hasChild;

    private Integer parentId;

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<ProjectDataModel> getChilds() {
        return childs;
    }

    public void setChilds(List<ProjectDataModel> childs) {
        this.childs = childs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
