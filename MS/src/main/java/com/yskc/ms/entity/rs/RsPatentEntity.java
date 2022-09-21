package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.yskc.ms.entity.rs.models.RsBaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/1 8:31
 * description:专利
 */
@TableName("patent")
public class RsPatentEntity extends RsBaseEntity implements Serializable {

    @TableId
    private Integer id;
    /**
     * 专利号
     */
    private String patentNo;
    /**
     * 专利名称
     */
    private String patentName;
    /**
     * 申请日期
     */
    private Date applyDateTime;
    /**
     * 类型
     */
    private String mainType;
    /**
     * 主分类号
     */
    private String mainTypeNo;
    /**
     * 案件状态
     */
    private String caseStatus;
    /**
     * 分案提交日
     */
    private Date caseSubmissionDate;
    /**
     * 发明人
     */
    private String inventor;
    /**
     * 截止缴费日期
     */
    private Date expiryDate;
    /**
     * 截至缴费金额/应缴金额
     */
    private BigDecimal expiryAmount;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer companyId;

    private Integer projectId;

    private Integer year;

    private Integer source;//0自主1购买

    private Integer claimNum;
    private String claimContent;
    private Integer usedCnt;
    private String specification;
    private Date authDate;
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public RsPatentEntity() {
    }

    public RsPatentEntity(String patentNo, String attribute, String attribute1, Integer msUserId, Date now) {
        this.patentName = patentNo;

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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getExpiryAmount() {
        return expiryAmount;
    }

    public void setExpiryAmount(BigDecimal expiryAmount) {
        this.expiryAmount = expiryAmount;
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
