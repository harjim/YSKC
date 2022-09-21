package com.yskc.ms.models.checkPayment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.contract.ContractNodeModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 10:27
 **/
public class CheckPaymentModel {
    private Integer id;
    private Integer customerId;
    private String companyName;
    private Integer year;
    private Integer deptId;
    private String deptName;
    private Integer ownerId;
    private String ownerName;
    private Integer techId;
    private String techName;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private String finaManagerName;
    private String finaDirectorName;
    private Integer ownerMangerId;
    private String ownerMangerName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;
    private Integer rdCnt;
    private Integer checkInstId;
    private String checkInstName;
    private String remark;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private String checkUsername;
    private String checkPassword;
    private String payType;
    private String paymentFile;

    private String nodeName;
    private Integer nodeNumber;
    private Integer instanceId;
    private Boolean hasPermission;
    private Integer status;//流程状态
    private String auditUsers;//当前处理人
    private String creatorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
    private List<PaymentProjectModel> list;
    private List<ContractNodeModel> nodeList;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<PaymentProjectModel> getList() {
        return list;
    }

    public void setList(List<PaymentProjectModel> list) {
        this.list = list;
    }

    public List<ContractNodeModel> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<ContractNodeModel> nodeList) {
        this.nodeList = nodeList;
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

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
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

    public Integer getOwnerMangerId() {
        return ownerMangerId;
    }

    public void setOwnerMangerId(Integer ownerMangerId) {
        this.ownerMangerId = ownerMangerId;
    }

    public String getOwnerMangerName() {
        return ownerMangerName;
    }

    public void setOwnerMangerName(String ownerMangerName) {
        this.ownerMangerName = ownerMangerName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getRdCnt() {
        return rdCnt;
    }

    public void setRdCnt(Integer rdCnt) {
        this.rdCnt = rdCnt;
    }

    public Integer getCheckInstId() {
        return checkInstId;
    }

    public void setCheckInstId(Integer checkInstId) {
        this.checkInstId = checkInstId;
    }

    public String getCheckInstName() {
        return checkInstName;
    }

    public void setCheckInstName(String checkInstName) {
        this.checkInstName = checkInstName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(String checkUsername) {
        this.checkUsername = checkUsername;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPaymentFile() {
        return paymentFile;
    }

    public void setPaymentFile(String paymentFile) {
        this.paymentFile = paymentFile;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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
}
