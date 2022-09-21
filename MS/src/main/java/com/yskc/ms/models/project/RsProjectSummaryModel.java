package com.yskc.ms.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 10:59
 * @Description:
 */
public class RsProjectSummaryModel implements Serializable {

    private Integer rsProjectId;

    private Integer companyId;

    private String companyName;

    private Integer year;

    private String pname;

    private String rdTitle;

    private Integer memberCnt;

    private Integer equipmentCnt;

    private Integer patentCnt;

    private Boolean hasReport;

    private Integer reportStatus;

    private Boolean hasNewReport;

    private Integer newReportStatus;

    private Integer docTotal;

    private Integer docSubmitCnt;

    private Integer docDoneCnt;

    private Integer backupDataTotalCnt;

    private String workshop;

    private String processSection;

    private String productLine;

    private String realName;

    private String groupName;

    /**
     * 财务负责人
     */
    private String financeName;

    private String fullname;

    private String deptName;

    private Integer deptId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }

    public Integer getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(Integer equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Boolean getHasNewReport() {
        return hasNewReport;
    }

    public void setHasNewReport(Boolean hasNewReport) {
        this.hasNewReport = hasNewReport;
    }

    public Integer getNewReportStatus() {
        return newReportStatus;
    }

    public void setNewReportStatus(Integer newReportStatus) {
        this.newReportStatus = newReportStatus;
    }

    public Integer getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(Integer docTotal) {
        this.docTotal = docTotal;
    }

    public Integer getDocSubmitCnt() {
        return docSubmitCnt;
    }

    public void setDocSubmitCnt(Integer docSubmitCnt) {
        this.docSubmitCnt = docSubmitCnt;
    }

    public Integer getDocDoneCnt() {
        return docDoneCnt;
    }

    public void setDocDoneCnt(Integer docDoneCnt) {
        this.docDoneCnt = docDoneCnt;
    }

    public Integer getBackupDataTotalCnt() {
        return backupDataTotalCnt;
    }

    public void setBackupDataTotalCnt(Integer backupDataTotalCnt) {
        this.backupDataTotalCnt = backupDataTotalCnt;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
