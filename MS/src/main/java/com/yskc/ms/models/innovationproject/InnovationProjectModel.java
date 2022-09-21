package com.yskc.ms.models.innovationproject;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.innovationproject
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-24 10:52
 * @Description: 创新项目model
 */
public class InnovationProjectModel {

    private Integer id;

    private String companyName;

    private Integer customerId;

    private Integer year;

    private Integer companyId;

    private String deptName;

    private String owerName;

    private String techRealName;

    private String financeRealName;

    private Integer cnt;

    private Integer rdCount;

    private Integer lastRdCnt;
    private Integer patentApplyCnt;
    private Integer buildCnt;
    private String types;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operationTime;

    private Integer reportDoneCnt;
    private Integer reportOngoingCnt;

    private String typesStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public Integer getLastRdCnt() {
        return lastRdCnt;
    }

    public void setLastRdCnt(Integer lastRdCnt) {
        this.lastRdCnt = lastRdCnt;
    }

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }

    public Integer getBuildCnt() {
        return buildCnt;
    }

    public void setBuildCnt(Integer buildCnt) {
        this.buildCnt = buildCnt;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getReportDoneCnt() {
        return reportDoneCnt;
    }

    public void setReportDoneCnt(Integer reportDoneCnt) {
        this.reportDoneCnt = reportDoneCnt;
    }

    public Integer getReportOngoingCnt() {
        return reportOngoingCnt;
    }

    public void setReportOngoingCnt(Integer reportOngoingCnt) {
        this.reportOngoingCnt = reportOngoingCnt;
    }

    public String getTypesStr() {
        return typesStr;
    }

    public void setTypesStr(String typesStr) {
        this.typesStr = typesStr;
    }
}
