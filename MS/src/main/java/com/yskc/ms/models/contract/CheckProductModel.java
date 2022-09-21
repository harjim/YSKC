package com.yskc.ms.models.contract;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-01 13:55
 **/
public class CheckProductModel {
    private List<ContractProjectModel> projectList;
    private Integer customerId;

    public List<ContractProjectModel> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ContractProjectModel> projectList) {
        this.projectList = projectList;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
