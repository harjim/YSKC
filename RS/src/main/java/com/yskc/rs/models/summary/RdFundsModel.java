package com.yskc.rs.models.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.summary
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-18 09:12
 * @Description: 项目费用model
 */
public class RdFundsModel {
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Date month;
    /**
     *
     */
    private Integer rdType;
    /**
     *
     */
    private BigDecimal rdFunds;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
