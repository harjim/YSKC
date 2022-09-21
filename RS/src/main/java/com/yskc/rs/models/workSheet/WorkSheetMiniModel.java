package com.yskc.rs.models.workSheet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workSheet
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-21 17:12
 * @Description: 工单mini
 */
public class WorkSheetMiniModel implements Serializable {

    private BigDecimal cost;

    private Integer rdIndex;

    private Date workDate;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }
}
