package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/5/5 13:52
 * 客户服务表
 */
@TableName("serviceLog")
public class ServiceLog {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    private Integer customerId;//客户id
    private Integer serviceType;//服务类型
    private BigDecimal totalTime;//服务总工时
    private Integer totalStaff;//服务总人数
    private String lastProblem;//之前遇到问题及解决方案
    private String nowProblem;//本次遇到的问题
    private String customerAdvice;//客户建议
    private String customerParticipan;//客户参与人员
    private String serviceDept;//服务的具体车间/部门
    private Integer status;//提交状态0未提交1提交
    private Date startDate;//服务开始时间
    private Date endDate;//服务结束时间
    private String auditOpinion;
    private String filePath;
    private Date submitDate;
    private Integer days;
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BigDecimal totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(Integer totalStaff) {
        this.totalStaff = totalStaff;
    }

    public String getLastProblem() {
        return lastProblem;
    }

    public void setLastProblem(String lastProblem) {
        this.lastProblem = lastProblem;
    }

    public String getNowProblem() {
        return nowProblem;
    }

    public void setNowProblem(String nowProblem) {
        this.nowProblem = nowProblem;
    }

    public String getCustomerAdvice() {
        return customerAdvice;
    }

    public void setCustomerAdvice(String customerAdvice) {
        this.customerAdvice = customerAdvice;
    }

    public String getCustomerParticipan() {
        return customerParticipan;
    }

    public void setCustomerParticipan(String customerParticipan) {
        this.customerParticipan = customerParticipan;
    }

    public String getServiceDept() {
        return serviceDept;
    }

    public void setServiceDept(String serviceDept) {
        this.serviceDept = serviceDept;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
