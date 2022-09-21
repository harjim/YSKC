package com.yskc.ms.models.servicelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.OtherUsersModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/5/5 18:41
 */
public class ServiceInfoModel {

    private Integer id;
    private String customerName;//客户名称
    private Integer customerId;//客户名称
    private String serviceType;//服务类型
    private BigDecimal totalTime;//服务总工时
    private Integer totalStaff;//服务总人数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startDate;//服务开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date endDate;//服务结束时间
    private String realName;
    private String otherStaffName;//其他服务人员姓名
    private String mainStaffName;//主要服务人员姓名
    private Integer mType;//人员类型
    private Integer status; //提交状态
    private String lastProblem;
    private String nowProblem;
    private String customerAdvice;
    private String customerParticipan;
    private String serviceDept;
    private String filePath;
    private List<OtherUsersModel> otherUsersModels;
    private Integer mainUserId;
    private List<String> filePaths;//上传文件路径列表
    private String auditOpinion;//审核意见
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date submitDate;
    private Integer days;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public Integer getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(Integer mainUserId) {
        this.mainUserId = mainUserId;
    }

    public List<OtherUsersModel> getOtherUsersModels() {
        return otherUsersModels;
    }

    public void setOtherUsersModels(List<OtherUsersModel> otherUsersModels) {
        this.otherUsersModels = otherUsersModels;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
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

    public String getOtherStaffName() {
        return otherStaffName;
    }

    public void setOtherStaffName(String otherStaffName) {
        this.otherStaffName = otherStaffName;
    }

    public String getMainStaffName() {
        return mainStaffName;
    }

    public void setMainStaffName(String mainStaffName) {
        this.mainStaffName = mainStaffName;
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
