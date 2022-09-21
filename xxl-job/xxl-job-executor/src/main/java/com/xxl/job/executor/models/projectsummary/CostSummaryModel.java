package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

import java.math.BigDecimal;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 16:04
 * @Description: 成本统计
 */
public class CostSummaryModel extends BaseSummaryModel {
    /**
     * 成本工资
     */
    private BigDecimal c10000;
    /**
     * 成本五险一金
     */
    private BigDecimal c10100;
    /**
     * 成本材料
     */
    private BigDecimal c20000;
    /**
     * 成本动力
     */
    private BigDecimal c20100;
    /**
     * 成本燃料
     */
    private BigDecimal c20200;
    /**
     * 成本试制
     */
    private BigDecimal c20300;
    /**
     * 成本检测
     */
    private BigDecimal c20500;
    /**
     * 成本修理
     */
    private BigDecimal c20600;
    /**
     * 成本样机
     */
    private BigDecimal c20700;
    /**
     * 成本设备折旧(300,301)
     */
    private BigDecimal c30000;
    /**
     * 成本软件摊销
     */
    private BigDecimal c40000;
    /**
     * 成本其他摊销(401,402)
     */
    private BigDecimal c40200;
    /**
     * 成本设计
     */
    private BigDecimal c50000;
    /**
     * 成本其他(所有>=60000)
     */
    private BigDecimal c69900;

    public BigDecimal getC10000() {
        return c10000;
    }

    public void setC10000(BigDecimal c10000) {
        this.c10000 = c10000;
    }

    public BigDecimal getC10100() {
        return c10100;
    }

    public void setC10100(BigDecimal c10100) {
        this.c10100 = c10100;
    }

    public BigDecimal getC20000() {
        return c20000;
    }

    public void setC20000(BigDecimal c20000) {
        this.c20000 = c20000;
    }

    public BigDecimal getC20100() {
        return c20100;
    }

    public void setC20100(BigDecimal c20100) {
        this.c20100 = c20100;
    }

    public BigDecimal getC20200() {
        return c20200;
    }

    public void setC20200(BigDecimal c20200) {
        this.c20200 = c20200;
    }

    public BigDecimal getC20300() {
        return c20300;
    }

    public void setC20300(BigDecimal c20300) {
        this.c20300 = c20300;
    }

    public BigDecimal getC20500() {
        return c20500;
    }

    public void setC20500(BigDecimal c20500) {
        this.c20500 = c20500;
    }

    public BigDecimal getC20600() {
        return c20600;
    }

    public void setC20600(BigDecimal c20600) {
        this.c20600 = c20600;
    }

    public BigDecimal getC20700() {
        return c20700;
    }

    public void setC20700(BigDecimal c20700) {
        this.c20700 = c20700;
    }

    public BigDecimal getC30000() {
        return c30000;
    }

    public void setC30000(BigDecimal c30000) {
        this.c30000 = c30000;
    }

    public BigDecimal getC40000() {
        return c40000;
    }

    public void setC40000(BigDecimal c40000) {
        this.c40000 = c40000;
    }

    public BigDecimal getC40200() {
        return c40200;
    }

    public void setC40200(BigDecimal c40200) {
        this.c40200 = c40200;
    }

    public BigDecimal getC50000() {
        return c50000;
    }

    public void setC50000(BigDecimal c50000) {
        this.c50000 = c50000;
    }

    public BigDecimal getC69900() {
        return c69900;
    }

    public void setC69900(BigDecimal c69900) {
        this.c69900 = c69900;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        BigDecimal costAmount = BigDecimal.ZERO;
        if (null != c10000) {
            costAmount = costAmount.add(c10000);
            ps.setC10000(c10000);
        }
        if (null != c10100) {
            costAmount = costAmount.add(c10100);
            ps.setC10100(c10100);
        }
        if (null != c20000) {
            costAmount = costAmount.add(c20000);
            ps.setC20000(c20000);
        }
        if (null != c20100) {
            costAmount = costAmount.add(c20100);
            ps.setC20100(c20100);
        }
        if (null != c20200) {
            costAmount = costAmount.add(c20200);
            ps.setC20200(c20200);
        }
        if (null != c20300) {
            costAmount = costAmount.add(c20300);
            ps.setC20300(c20300);
        }
        if (null != c20500) {
            costAmount = costAmount.add(c20500);
            ps.setC20500(c20500);
        }
        if (null != c20600) {
            costAmount = costAmount.add(c20600);
            ps.setC20600(c20600);
        }
        if (null != c20700) {
            costAmount = costAmount.add(c20700);
            ps.setC20700(c20700);
        }
        if (null != c30000) {
            costAmount = costAmount.add(c30000);
            ps.setC30000(c30000);
        }
        if (null != c40000) {
            costAmount = costAmount.add(c40000);
            ps.setC40000(c40000);
        }
        if (null != c40200) {
            costAmount = costAmount.add(c40200);
            ps.setC40200(c40200);
        }
        if (null != c50000) {
            costAmount = costAmount.add(c50000);
            ps.setC50000(c50000);
        }
        if (null != c69900) {
            costAmount = costAmount.add(c69900);
            ps.setC69900(c69900);
        }
        ps.setCostAmount(costAmount);
    }
}
