package com.yskc.ms.models.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 10:59
 **/
public class ContractModel {
    private Integer id;
    private String contractNo;
    private Integer customerId;
    private String companyName;
    private Integer deptId;
    private String deptName;
    private Integer projectCnt;
    private Integer partnerCnt;
    private Integer ownerId;
    private String ownerName;
    private Integer techId;
    private String techName;
    private Integer finaId;
    private String finaName;
    private Integer businessId;
    private String businessName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date closeDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Size(max=200,message="文件路径不能超过200个字")
    private String filepath;
    private String qrcode;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;
//    private List<Integer> sealTypes;
    private String sealType;
    private String expressNo;
    private String expressAddress;

    private String nodeName;
    private Integer nodeNumber;

    private Integer instanceId;
    private Boolean hasPermission;
    private Integer status;//流程状态
    private String auditUsers;//当前处理人
    private List<ContractMemberModel> memberList;
    private List<ContractProjectModel> projectList;
    private List<ContractNodeModel> nodeList;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(String expressAddress) {
        this.expressAddress = expressAddress;
    }

    public List<ContractNodeModel> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<ContractNodeModel> nodeList) {
        this.nodeList = nodeList;
    }

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public Integer getProjectCnt() {
        return projectCnt;
    }

    public void setProjectCnt(Integer projectCnt) {
        this.projectCnt = projectCnt;
    }

    public Integer getPartnerCnt() {
        return partnerCnt;
    }

    public void setPartnerCnt(Integer partnerCnt) {
        this.partnerCnt = partnerCnt;
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

    public Integer getFinaId() {
        return finaId;
    }

    public void setFinaId(Integer finaId) {
        this.finaId = finaId;
    }

    public String getFinaName() {
        return finaName;
    }

    public void setFinaName(String finaName) {
        this.finaName = finaName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ContractMemberModel> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ContractMemberModel> memberList) {
        this.memberList = memberList;
    }

    public List<ContractProjectModel> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ContractProjectModel> projectList) {
        this.projectList = projectList;
    }
}
