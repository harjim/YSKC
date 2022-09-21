package com.yskc.ms.models.patentPlan;

/**
 * 专利申请统计model
 */
public class PatentPlanStatModel {
    /**
     * 需求Id
     */
    private Integer demandId;
    /**
     * 申请总数
     */
    private Integer noOfplan;

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getNoOfplan() {
        return noOfplan;
    }

    public void setNoOfplan(Integer noOfplan) {
        this.noOfplan = noOfplan;
    }
}
