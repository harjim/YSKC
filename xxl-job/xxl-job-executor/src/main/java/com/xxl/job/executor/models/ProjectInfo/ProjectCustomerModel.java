package com.xxl.job.executor.models.ProjectInfo;

/**
 * Created by hck
 * on 2020/11/4 9:33
 * description:
 */
public class ProjectCustomerModel {

    private Integer id;

    private Integer customerId;

    private Integer companyId;

    private Integer year;

    public ProjectCustomerModel(Integer companyId, Integer year) {
        this.companyId = companyId;
        this.year = year;
    }

    public ProjectCustomerModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
