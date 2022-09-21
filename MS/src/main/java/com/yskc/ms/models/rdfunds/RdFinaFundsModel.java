package com.yskc.ms.models.rdfunds;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/4/26 8:56
 * @Description:
 * @author: hsx
 */
public class RdFinaFundsModel implements Serializable {

    private Integer id;

    private String rdTitle;

    private String pname;

    private Integer rdType;

    private Integer projectId;

    private Integer status;

    private Integer instanceId;

    private Boolean hasPermision;

    private BigDecimal totalAmount;
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private String auditUsers;  //当前审核人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Boolean getHasPermision() {
        return hasPermision;
    }

    public void setHasPermision(Boolean hasPermision) {
        this.hasPermision = hasPermision;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public void addTotal() {
    }

}
