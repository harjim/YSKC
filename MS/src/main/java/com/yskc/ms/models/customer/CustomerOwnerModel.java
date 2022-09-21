package com.yskc.ms.models.customer;

import com.yskc.ms.models.serviceApply.ServiceApplyModel;
import com.yskc.ms.models.serviceApply.ServiceNoModel;

import java.util.List;

/**
 * @program: ms
 * @description: 客户业务员model
 * @author: wjy
 * @create: 2022-08-13 11:45
 **/
public class CustomerOwnerModel {
    private Integer customerId;
    private String companyName;

    private Integer ownerId;
    private String ownerName;
    private Integer deptId;
    private String deptName;


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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
