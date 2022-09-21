package com.xxl.job.executor.models.ProjectInfo;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.ProjectInfo
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-13 09:27
 * @Description: 项目预算model
 */
public class ProjectBudgetModel {

    private Integer id;
    private Date endDate;
    /**
     * 最小归集月份
     */
    private Date minMonth;
    /**
     * 最大归集月
     */
    private Date maxMonth;

    private Date nextMonth;
    private Integer estimateExpense;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getMinMonth() {
        return minMonth;
    }

    public void setMinMonth(Date minMonth) {
        this.minMonth = minMonth;
    }

    public Date getMaxMonth() {
        return maxMonth;
    }

    public void setMaxMonth(Date maxMonth) {
        this.maxMonth = maxMonth;
    }

    public Integer getEstimateExpense() {
        return null == estimateExpense ? 0 : estimateExpense * 10000;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
    }

    public Date getNextMonth() {
        if (null != nextMonth) {
            return nextMonth;
        }
        return minMonth != null ? DateUtil.offsetMonth(minMonth, 2) : null;
    }

    public void setNextMonth(Date nextMonth) {
        this.nextMonth = nextMonth;
    }

    public void addBudget(int budget) {
        if (null == estimateExpense) {
            estimateExpense = budget;
        } else {
            estimateExpense += budget;
        }
    }
}
