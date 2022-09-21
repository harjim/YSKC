package com.yskc.ms.models.serviceApply;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description: 服务申请列
 * @author: cyj
 * @create: 2022-08-11 09:12
 **/
public class ServiceApplyModel {
    private Integer id;
    private String serviceNo;
    private Integer ownerId;
    private String ownerName;
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date begin;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end;
    private String date;
    private String remark;
    private String customers;

    private Integer techManagerId;
    private Integer techDirectorId;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private String techManagerName;
    private String techDirectorName;
    private String finaManagerName;
    private String finaDirectorName;

    private Integer instanceId;
    private Boolean hasPermission;
    private Integer status;//流程状态
    private String auditUsers;//当前处理人

    private Date createTime;
    private Date lastUpdateTime;

    private List<ServiceMemberModel> techList;
    private List<ServiceMemberModel> finaList;
    private List<ServiceMemberModel> otherList;
    private List<ServiceCustomerModel> customerList;

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getTechManagerName() {
        return techManagerName;
    }

    public void setTechManagerName(String techManagerName) {
        this.techManagerName = techManagerName;
    }

    public String getTechDirectorName() {
        return techDirectorName;
    }

    public void setTechDirectorName(String techDirectorName) {
        this.techDirectorName = techDirectorName;
    }

    public String getFinaManagerName() {
        return finaManagerName;
    }

    public void setFinaManagerName(String finaManagerName) {
        this.finaManagerName = finaManagerName;
    }

    public String getFinaDirectorName() {
        return finaDirectorName;
    }

    public void setFinaDirectorName(String finaDirectorName) {
        this.finaDirectorName = finaDirectorName;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ServiceMemberModel> getTechList() {
        return techList;
    }

    public void setTechList(List<ServiceMemberModel> techList) {
        this.techList = techList;
    }

    public List<ServiceMemberModel> getFinaList() {
        return finaList;
    }

    public void setFinaList(List<ServiceMemberModel> finaList) {
        this.finaList = finaList;
    }

    public List<ServiceMemberModel> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<ServiceMemberModel> otherList) {
        this.otherList = otherList;
    }

    public List<ServiceCustomerModel> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<ServiceCustomerModel> customerList) {
        this.customerList = customerList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public Integer getTechManagerId() {
        return techManagerId;
    }

    public void setTechManagerId(Integer techManagerId) {
        this.techManagerId = techManagerId;
    }

    public Integer getTechDirectorId() {
        return techDirectorId;
    }

    public void setTechDirectorId(Integer techDirectorId) {
        this.techDirectorId = techDirectorId;
    }

    public Integer getFinaManagerId() {
        return finaManagerId;
    }

    public void setFinaManagerId(Integer finaManagerId) {
        this.finaManagerId = finaManagerId;
    }

    public Integer getFinaDirectorId() {
        return finaDirectorId;
    }

    public void setFinaDirectorId(Integer finaDirectorId) {
        this.finaDirectorId = finaDirectorId;
    }
}
