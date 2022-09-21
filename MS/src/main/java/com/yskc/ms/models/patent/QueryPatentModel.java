package com.yskc.ms.models.patent;

import com.yskc.ms.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: wangxing
 * @CreateTime: 2020-01-10 09:00
 * @Description:
 */
public class QueryPatentModel extends PageParams {
    private Integer demandId;
    private String patentNo;
    private String patentName;
    private String mainType;
    private String inventor;
    private String applyDateStartTime;
    private String applyDateEndTime;
    private String startExpiryDate;
    private String endExpiryDate;
    private Date startTime;
    private Date entTime;
    private Date startDate;
    private Date entDate;
    private Integer status;//购买状态
    private String companyName;//客户名称

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getApplyDateStartTime() {
        return applyDateStartTime;
    }

    public void setApplyDateStartTime(String applyDateStartTime) {
        this.applyDateStartTime = applyDateStartTime;
    }

    public String getApplyDateEndTime() {
        return applyDateEndTime;
    }

    public void setApplyDateEndTime(String applyDateEndTime) {
        this.applyDateEndTime = applyDateEndTime;
    }

    public String getStartExpiryDate() {
        return startExpiryDate;
    }

    public void setStartExpiryDate(String startExpiryDate) {
        this.startExpiryDate = startExpiryDate;
    }

    public String getEndExpiryDate() {
        return endExpiryDate;
    }

    public void setEndExpiryDate(String endExpiryDate) {
        this.endExpiryDate = endExpiryDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEntDate() {
        return entDate;
    }

    public void setEntDate(Date entDate) {
        this.entDate = entDate;
    }
}
