package com.yskc.rs.models.design;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/21 13:58
 * description:
 */
public class DesignAmountModel implements Serializable {
    private List<Integer> ids;

    private Date projectMonth;

    private Integer projectId;

    private BigDecimal rdAmount;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
