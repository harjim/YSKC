package com.yskc.ms.models.patentPlan;

import java.io.Serializable;

/**
 * @program: ms
 * @description: 专利统计
 * @author: wjy
 * @create: 2022-07-06 09:45
 **/
public class PatentPlanTotalModel implements Serializable {
    private Integer demand;
    private Integer technical;
    private Integer finalized;
    private Integer accepled;
    private Integer authorize;

    public Integer getDemand() {
        return demand;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public Integer getTechnical() {
        return technical;
    }

    public void setTechnical(Integer technical) {
        this.technical = technical;
    }

    public Integer getFinalized() {
        return finalized;
    }

    public void setFinalized(Integer finalized) {
        this.finalized = finalized;
    }

    public Integer getAccepled() {
        return accepled;
    }

    public void setAccepled(Integer accepled) {
        this.accepled = accepled;
    }

    public Integer getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Integer authorize) {
        this.authorize = authorize;
    }
}
