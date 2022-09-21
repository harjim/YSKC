package com.yskc.ms.models.company;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/5 8:45
 * description:政府扶持情况model
 */
public class SupportModel implements Serializable {

    private Integer id;

    private Integer companyId;
    @NotNull(message = "项目名称不能为空")
    @NotEmpty(message = "项目名称不能为空")
    private String projectName;//项目名称
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private String supportTime;//扶持时间
    @NotNull(message = "资助单位不能为空")
    @NotEmpty(message = "资助单位不能为空")
    private String supportDeptName;//扶持部门
    @NotNull(message = "资助金额不能为空")
    private BigDecimal supportAmount;//扶持金额（万元）
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkTime;//验收时间
    private String checkResult;//验收结果
    private String remark;//备注
    @NotNull(message = "扶持年份不能为空")
    private Integer syear;//扶持年份
    @NotNull(message = "下达文号不能为空")
    @NotEmpty(message = "下达文号不能为空")
    private String issuceNum;//下达字号
    @NotNull(message = "项目负责人不能为空")
    @NotEmpty(message = "项目负责人不能为空")
    private String master;//负责人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupportTime() {
        return supportTime;
    }

    public void setSupportTime(String supportTime) {
        this.supportTime = supportTime;
    }

    public String getSupportDeptName() {
        return supportDeptName;
    }

    public void setSupportDeptName(String supportDeptName) {
        this.supportDeptName = supportDeptName;
    }

    public BigDecimal getSupportAmount() {
        return supportAmount;
    }

    public void setSupportAmount(BigDecimal supportAmount) {
        this.supportAmount = supportAmount;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSyear() {
        return syear;
    }

    public void setSyear(Integer syear) {
        this.syear = syear;
    }

    public String getIssuceNum() {
        return issuceNum;
    }

    public void setIssuceNum(String issuceNum) {
        this.issuceNum = issuceNum;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
