package com.yskc.rs.models.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.summary
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-29 14:53
 * @Description: 研究开发项目情况model
 */
public class RdSituationModel implements Serializable {
    /**
     * 序号
     */
    private Integer num;

    private Integer id;

    private Integer parentId;

    /**
     * 项目名
     */
    private String pname;
    /**
     * 项目来源
     */
    private Integer projectSource;
    /**
     * 发展形势
     */
    private Integer formula;
    /**
     * 成果形式
     */
    private String result;
    /**
     * 目标
     */
    private Integer targets;
    @JsonFormat(pattern = "yyyyMM", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyyMM", timezone = "GMT+8")
    private Date endDate;

    private String beginDateStr;

    private String endDateStr;
    /**
     * 项目阶段
     */
    private Integer stage;
    /**
     * 项目参与人数
     */
    private Integer employee;
    /**
     * 平均工时（人月）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avgWork;
    /**
     * 总花费
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cost;
    /**
     * 政府资金
     */
    private BigDecimal govCost;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(Integer projectSource) {
        this.projectSource = projectSource;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTargets() {
        return targets;
    }

    public void setTargets(Integer targets) {
        this.targets = targets;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public BigDecimal getAvgWork() {
        return avgWork;
    }

    public void setAvgWork(BigDecimal avgWork) {
        this.avgWork = avgWork;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getGovCost() {
        return govCost;
    }

    public void setGovCost(BigDecimal govCost) {
        this.govCost = govCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void addCost(BigDecimal cost) {
        if (this.cost == null) {
            this.cost = cost;
        } else {
            this.cost = this.cost.add(cost);
        }
    }

    public void addEmployee(Integer employee) {
        if (this.employee == null) {
            this.employee = employee;
        } else {
            this.employee += employee;
        }
    }

    public void addTotalWork(BigDecimal totalWork) {
        if (this.avgWork == null) {
            this.avgWork = totalWork;
        } else {
            this.avgWork = this.avgWork.add(totalWork);
        }
    }
}
