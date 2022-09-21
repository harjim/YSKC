package com.yskc.ms.models.serviceRecord;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description: 客户服务记录（新）
 * @author: cyj
 * @create: 2022-08-13 09:16
 **/
public class WorkRecordModel {
    private Integer id;
    private Integer customerId;
    private String companyName;
    private String serviceNo;
    private Integer ownerId;
    private String ownerName;
    private Integer deptId;
    private String deptName;
    private Integer itemCnt;
    private BigDecimal totalAmount;

    private Integer instanceId;
    private Boolean hasPermission;
    private Integer status;//流程状态
    private String auditUsers;//当前处理人

    private String creatorName;
    private Date createTime;
    private Date lastUpdateTime;

    private List<WorkItemModel> list;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(Integer itemCnt) {
        this.itemCnt = itemCnt;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
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

    public List<WorkItemModel> getList() {
        return list;
    }

    public void setList(List<WorkItemModel> list) {
        this.list = list;
    }
}
