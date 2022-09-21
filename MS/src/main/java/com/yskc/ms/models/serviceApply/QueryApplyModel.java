package com.yskc.ms.models.serviceApply;

import com.yskc.ms.models.params.PageParams;

import java.util.Date;

/**
 * @program: ms
 * @description: 请求服务申请列
 * @author: cyj
 * @create: 2022-08-11 10:40
 **/
public class QueryApplyModel extends PageParams {
    private String companyName;
    private Integer techId;
    private Integer finaId;
    private Integer ownerId;
    private String serviceNo;
    private String auditUsers;
    private Integer deptId;
    private Integer status;
    private Date lastUpdateTime;

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getFinaId() {
        return finaId;
    }

    public void setFinaId(Integer finaId) {
        this.finaId = finaId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

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
}
