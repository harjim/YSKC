package com.yskc.ms.models.patent;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: wangxing
 * @CreateTime: 2019-10-25 16:28
 * @Description: 专利Model
 */
public class PatentModel {
    private Integer id;
    private String patentNo;
    private String patentName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDateTime;
    private String mainType;
    private String mainTypeNo;
    private String caseStatus;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date caseSubmissionDate;
    private String inventor;
    private Integer customerId;
    private String applyName;
    private String address;
    private String companyIdArry;
    private String companyName;
    private Integer companyId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
    private Integer source;//0 自主 1 购买

    private Integer claimNum;
    private String claimContent;
    private Integer usedCnt;
    private String specification;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date authDate;
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 应缴费用
     */
    private BigDecimal payable;
    /**
     * 最近的截止缴费日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expiryDate;

    /**
     * 应缴费用
     */
    private BigDecimal expiryAmount;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getExpiryAmount() {
        return expiryAmount;
    }

    public void setExpiryAmount(BigDecimal expiryAmount) {
        this.expiryAmount = expiryAmount;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCompanyIdArry() {
        return companyIdArry;
    }

    public void setCompanyIdArry(String companyIdArry) {
        this.companyIdArry = companyIdArry;
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

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getMainTypeNo() {
        return mainTypeNo;
    }

    public void setMainTypeNo(String mainTypeNo) {
        this.mainTypeNo = mainTypeNo;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Date getCaseSubmissionDate() {
        return caseSubmissionDate;
    }

    public void setCaseSubmissionDate(Date caseSubmissionDate) {
        this.caseSubmissionDate = caseSubmissionDate;
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
}
