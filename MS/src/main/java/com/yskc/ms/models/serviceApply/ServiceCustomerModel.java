package com.yskc.ms.models.serviceApply;

/**
 * @program: ms
 * @description: 服务的客户
 * @author: cyj
 * @create: 2022-08-11 09:37
 **/
public class ServiceCustomerModel {
    private Integer id;
    private Integer serviceApplyId;
    private Integer customerId;
    private String companyName;
    private String causes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceApplyId() {
        return serviceApplyId;
    }

    public void setServiceApplyId(Integer serviceApplyId) {
        this.serviceApplyId = serviceApplyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }
}
