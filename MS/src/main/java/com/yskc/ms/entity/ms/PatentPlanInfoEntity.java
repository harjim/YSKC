package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/2/23 9:56
 * @Description:
 * @author: hsx
 */
@TableName("patent_plan_info")
public class PatentPlanInfoEntity implements Serializable {

    private static final long serialVersionUID = 6547138782046715896L;

    @TableId
    private Integer id;

    //专利申请id
    private Integer patentPlanId;

    //提交国知局日期
    private Date submittedDate;

    //受理通知日期
    private Date acceptNoticeDate;

    //受理缴费日期
    private Date acceptFeeDate;

    //受理费用
    private BigDecimal acceptFee;

    //授权日期(保存该日期时，需要同时更改rsdb.patent.authDate)
    private Date authDate;

    //授权缴费日期
    private Date authFeeDate;

    //授权费用
    private BigDecimal authFee;

    //下证日期
    private Date issueDate;

    private Integer creatorId;

    private Integer lastUpdatorId;

    private Date createTime;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
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

    public BigDecimal getAcceptFee() {
        return acceptFee;
    }

    public void setAcceptFee(BigDecimal acceptFee) {
        this.acceptFee = acceptFee;
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

    public BigDecimal getAuthFee() {
        return authFee;
    }

    public void setAuthFee(BigDecimal authFee) {
        this.authFee = authFee;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public void create(Date date, Integer userId) {
        this.creatorId = userId;
        this.lastUpdatorId = userId;
        this.createTime = date;
        this.lastUpdateTime = date;
    }

    public void update(Date date, Integer userId) {
        this.lastUpdatorId = userId;
        this.lastUpdateTime = date;
    }
}
