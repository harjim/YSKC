package com.yskc.ms.models.patentPlan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.entity.ms.PatentPlanProcess;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 11:53
 * @Description:
 */
public class PatentPlanModel implements Serializable {

    private Integer id;
    private Integer customerId;
    private String patentNo;
    private String patentName;// 申请名称
    private Integer patentType;// 专利类型 '1': '发明专利','2': '实用新型', '3': '外观设计'
    private Integer unitType;// 单位类型 0：工矿企业，1：个人，2大专院校，3：科研单位，4：事业单位
    private Integer planType;// 申请类型（版本）：0：高新版，1：保护版
    private Integer year;
    private Integer projectId;
    private String code;// 身份证号码或统一信息代码
    private String linkMan;// 联系人
    private String linkTel;// 联系方式
    private String postalCode;// 邮政编码
    private String otherItem;// 其他事项（多选） 0：发明+实用新型同时申请 1：申请时办理提前公开请求 2：是否办理费减
    private String address;// 详情地址
    private String firstInventor;// 第一发明人
    private String otherInventor;// 其他发明人
    private Integer masterId;// 负责人
    private String masterLinkTel;//负责人电话
    // 0：发起，1：审批，2：初稿，3：定稿:4：受理，5：授权
    private Integer process; // (新)0：技术交底，1：交底审批，2：代理人分配，3：定稿，4：受理，5:受理缴费，6：授权，7授权缴费
    private Integer status;// 0进行中 1通过 2驳回 3激活 4提交 5(null)未提交 6失败 7提交失败 [当且仅当 status=1，process 可进行 2(审核) 之后的操作]
    private Integer ownerId;// 业务员
    private String companyName;
    private String ownerName;
    private String masterName;
    private String rdTitle;
    private Integer companyId;
    private List<PatentFileModel> fileModels;//交底书
    private String remark;
    private Map<Integer, PatentPlanProcess> processMap;
    private Boolean hasPermission;
    private Integer engineerId;//工程师id
    private Integer deptId;
    private String deptName;//所属部门
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date demandDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date submitDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String applicant;//申请人
    private Integer userId;

    private String engineer;
    private String auditUsers;
    private Integer nodeNumber;
    private String demandNo;

    private String groupName;
    private Integer groupId;

    private Integer patentPlanInfoId;
    //实际提交国知局时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submittedDate;
    //受理通知日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptNoticeDate;
    //受理缴费日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptFeeDate;
    //授权日期(保存该日期时，需要同时更改rsdb.patent.authDate)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authDate;
    //授权缴费日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authFeeDate;
    //下证日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issueDate;

    //受理费用
    private BigDecimal acceptFee;

    //授权费用
    private BigDecimal authFee;

    private Integer instanceId;

    private Integer curNodeId;

    //需求Id
    private Integer demandId;
    private String confirmName;
    private Integer confirmType;

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public Integer getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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


    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Map<Integer, PatentPlanProcess> getProcessMap() {
        return processMap;
    }

    public void setProcessMap(Map<Integer, PatentPlanProcess> processMap) {
        this.processMap = processMap;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PatentFileModel> getFileModels() {
        return fileModels;
    }

    public void setFileModels(List<PatentFileModel> fileModels) {
        this.fileModels = fileModels;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
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

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public Integer getPatentType() {
        return patentType;
    }

    public void setPatentType(Integer patentType) {
        this.patentType = patentType;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOtherItem() {
        return otherItem;
    }

    public void setOtherItem(String otherItem) {
        this.otherItem = otherItem;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstInventor() {
        return firstInventor;
    }

    public void setFirstInventor(String firstInventor) {
        this.firstInventor = firstInventor;
    }

    public String getOtherInventor() {
        return otherInventor;
    }

    public void setOtherInventor(String otherInventor) {
        this.otherInventor = otherInventor;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Date getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(Date demandDate) {
        this.demandDate = demandDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getPatentPlanInfoId() {
        return patentPlanInfoId;
    }

    public void setPatentPlanInfoId(Integer patentPlanInfoId) {
        this.patentPlanInfoId = patentPlanInfoId;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Date getAcceptNoticeDate() {
        return acceptNoticeDate;
    }

    public void setAcceptNoticeDate(Date acceptNoticeDate) {
        this.acceptNoticeDate = acceptNoticeDate;
    }

    public Date getAcceptFeeDate() {
        return acceptFeeDate;
    }

    public void setAcceptFeeDate(Date acceptFeeDate) {
        this.acceptFeeDate = acceptFeeDate;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Date getAuthFeeDate() {
        return authFeeDate;
    }

    public void setAuthFeeDate(Date authFeeDate) {
        this.authFeeDate = authFeeDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getAcceptFee() {
        return acceptFee;
    }

    public void setAcceptFee(BigDecimal acceptFee) {
        this.acceptFee = acceptFee;
    }

    public BigDecimal getAuthFee() {
        return authFee;
    }

    public void setAuthFee(BigDecimal authFee) {
        this.authFee = authFee;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Integer curNodeId) {
        this.curNodeId = curNodeId;
    }

    public String getMasterLinkTel() {
        return masterLinkTel;
    }

    public void setMasterLinkTel(String masterLinkTel) {
        this.masterLinkTel = masterLinkTel;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }
}
