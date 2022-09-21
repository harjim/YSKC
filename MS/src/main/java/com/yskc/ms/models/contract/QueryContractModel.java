package com.yskc.ms.models.contract;

import com.yskc.ms.models.params.PageParams;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 11:48
 **/
public class QueryContractModel extends PageParams {
    private String companyName;
    private Integer status;
    private Integer nodeNumber;
    private Integer deptId;
    private Integer ownerId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
