package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

import java.math.BigDecimal;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 17:36
 * @Description: 高品统计
 */
public class HighTechSummaryModel extends BaseSummaryModel {
    /**
     * 高品数
     */
    private Integer highTechCount;

    /**
     * 高品收入
     */
    private BigDecimal highTechIncome;

    /**
     * 累计主营业务收入
     */
    private BigDecimal totalIncome;

    public Integer getHighTechCount() {
        return highTechCount;
    }

    public void setHighTechCount(Integer highTechCount) {
        this.highTechCount = highTechCount;
    }

    public BigDecimal getHighTechIncome() {
        return highTechIncome;
    }

    public void setHighTechIncome(BigDecimal highTechIncome) {
        this.highTechIncome = highTechIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        if (null != highTechCount) {
            ps.setHighTechCount(highTechCount);
        } else {
            ps.setHighTechIncome(highTechIncome);
            ps.setTotalIncome(totalIncome);
        }
    }
}
