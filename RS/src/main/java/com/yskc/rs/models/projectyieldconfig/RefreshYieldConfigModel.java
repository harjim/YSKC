package com.yskc.rs.models.projectyieldconfig;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-18 11:01
 * @Description: 刷新研发试制计划model
 */
public class RefreshYieldConfigModel {

    @NotNull(message = "获取项目失败，请稍后再试。")
    private Integer projectId;
    @NotNull(message = "获取月份失败，请稍后再试。")
    private Date month;
    private BigDecimal[] equDataArr;
    @NotNull(message = "试制开始时间不能为空")
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private Date trialTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    /**
     * ecode为了标识是通过那个设备生成的。
     */
    private String ecode;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal[] getEquDataArr() {
        return equDataArr;
    }

    public void setEquDataArr(BigDecimal[] equDataArr) {
        this.equDataArr = equDataArr;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
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

    public Date getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(Date trialTime) {
        this.trialTime = trialTime;
    }
}
