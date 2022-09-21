package com.yskc.ms.models.patentPlan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 16:08
 * @Description:
 */
public class PatentInfoModel implements Serializable {

    private Integer id;

    private String patentNo;

    private String patentName;

    private String mainType;

    private Date applyDateTime;

    private String inventor;

    private Integer companyId;

    private Integer projectId;

    private Integer year;

    private Integer patentPlanId;

    private String remark;

    private Integer claimNum;
    private String claimContent;
    private Integer usedCnt;
    private String specification;
    private Integer masterId;
    private Integer instanceId;

    private Integer patentPlanInfoId;
    //实际提交国知局时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submittedDate;
    //受理通知日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date acceptNoticeDate;
    //受理缴费日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date acceptFeeDate;
    //授权日期(保存该日期时，需要同时更改rsdb.patent.authDate)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date authDate;
    //授权缴费日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date authFeeDate;
    //下证日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date issueDate;

    //受理费用
    private BigDecimal acceptFee;

    //授权费用
    private BigDecimal authFee;
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getClaimNum() {
        return claimNum;
    }

    public void setClaimNum(Integer claimNum) {
        this.claimNum = claimNum;
    }

    public String getClaimContent() {
        return claimContent;
    }

    public void setClaimContent(String claimContent) {
        this.claimContent = claimContent;
    }

    public Integer getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(Integer usedCnt) {
        this.usedCnt = usedCnt;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
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

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }
}
