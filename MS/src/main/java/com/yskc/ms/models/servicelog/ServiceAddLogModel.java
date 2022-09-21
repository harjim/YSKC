package com.yskc.ms.models.servicelog;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/5/6 16:44
 */
public class ServiceAddLogModel {
    private Integer customerId;//客户id
    private Integer serviceType;//服务类型
    private BigDecimal totalTime;//服务总工时
    private Integer totalStaff;//服务总人数
    private String lastProblem;//之前遇到问题及解决方案
    private String nowProblem;//本次遇到的问题
    private String customerAdvice;//客户建议
    private String customerParticipan;//客户参与人员
    private String serviceDept;//服务的具体车间/部门
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date startDate;//服务开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date endDate;//服务结束时间
    private String staffIds;//其他服务人员
    private Integer mainStaffId;//主要服务人员id
    private String filePath;//文件地址
    private List<String> filePaths;
    private Integer changeStatus;
    private Integer days;

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }

    public Integer getMainStaffId() {
        return mainStaffId;
    }

    public void setMainStaffId(Integer mainStaffId) {
        this.mainStaffId = mainStaffId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
