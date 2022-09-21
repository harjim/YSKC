package com.yskc.rs.models.summary;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/6/18 11:56
 * description:
 */
public class SummaryBudgetModel implements Serializable {

    private BigDecimal summaryCost;//归集费用

    private BigDecimal budgetCost;//预算

    public BigDecimal getSummaryCost() {
        return summaryCost;
    }

    public void setSummaryCost(BigDecimal summaryCost) {
        this.summaryCost = summaryCost;
    }

    public BigDecimal getBudgetCost() {
        return budgetCost;
    }

    public void setBudgetCost(BigDecimal budgetCost) {
        this.budgetCost = budgetCost;
    }
}
