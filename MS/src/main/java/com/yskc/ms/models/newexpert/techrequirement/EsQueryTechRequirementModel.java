package com.yskc.ms.models.newexpert.techrequirement;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

public class EsQueryTechRequirementModel extends PageParams implements Serializable {

    /// 需求名称
    private String requirementName;

    // 客户名称(单位名称)
    private String customerName;

    // 领域分类
    private String researchArea;

    /**
     *  合作方式:
     *     0：技术转让，1：技术服务，2：技术许可
     *     3：技术融资，4：技术授权，5：其他
     */
    private Integer cooperateType;

    /**
     * 紧急程度 0:一周以内 1:一月以内 2:三个月以内
     * 3:六个月以内 4:一年以内 5:两年以上
     */
    private Integer urgency;

    private Integer msCreatorId;

    private Integer status;

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public Integer getCooperateType() {
        return cooperateType;
    }

    public void setCooperateType(Integer cooperateType) {
        this.cooperateType = cooperateType;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
