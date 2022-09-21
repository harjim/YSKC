package com.yskc.rs.models.projectinspection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.UsedModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目检测修理
 *
 * @author zhangdingfu
 */
public class ProjectInspectionModel implements Serializable {
    private Integer id;
    private Integer projectId;
    /**
     * 记帐日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accDate;
    /**
     * 0：检测，1：修理
     */
    public Integer type;
    /**
     * 摘要
     */
    public String summary;
    public BigDecimal expense;
    private String accNumber;
    private String deptName;

    private BigDecimal rdAmount;

    private BigDecimal remainExpense;//剩余金额

    private List<UsedModel> usedModels;

    private Integer inspectionDataId;

    public List<UsedModel> getUsedModels() {
        return usedModels;
    }

    public void setUsedModels(List<UsedModel> usedModels) {
        this.usedModels = usedModels;
    }

    public Integer getInspectionDataId() {
        return inspectionDataId;
    }

    public void setInspectionDataId(Integer inspectionDataId) {
        this.inspectionDataId = inspectionDataId;
    }

    public BigDecimal getRemainExpense() {
        return remainExpense;
    }

    public void setRemainExpense(BigDecimal remainExpense) {
        this.remainExpense = remainExpense;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getAccDate() {
        return accDate;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
