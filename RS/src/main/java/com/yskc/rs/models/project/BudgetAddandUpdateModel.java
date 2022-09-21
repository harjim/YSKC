package com.yskc.rs.models.project;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.Budget
 * @Author: wangxing
 * @CreateTime: 2019-10-29 11:10
 * @Description: BudgetModel
 */
public class BudgetAddandUpdateModel {

    private Integer projectId;

    private List<BudgetModel> budgetEntities;

    private Date month;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<BudgetModel> getBudgetEntities() {
        return budgetEntities;
    }

    public void setBudgetEntities(List<BudgetModel> budgetEntities) {
        this.budgetEntities = budgetEntities;
    }
}
