package com.yskc.rs.models.project;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @DateTime: 2021/9/2 16:43
 * @Description:
 */
public class ProjectCollectModel implements Serializable {

    private String projectName;
    private String estimateExpense;
    private String type;
    private String status;
    private Long numbers;
    private String durationTime;
    private String rdTitle;
    private Boolean present;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String ProjectName) {
        this.projectName = ProjectName;
    }

    public String getEstimateExpense() {
        return estimateExpense;
    }

    public void setEstimateExpense(String estimateExpense) {
        this.estimateExpense = estimateExpense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumbers() {
        return numbers;
    }

    public void setNumbers(Long numbers) {
        this.numbers = numbers;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
