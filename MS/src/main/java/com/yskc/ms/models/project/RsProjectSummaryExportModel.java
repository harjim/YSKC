package com.yskc.ms.models.project;

import java.io.Serializable;

/**
 * @DateTime: 2022/3/23 17:01
 * @Description:
 * @author: hsx
 */
public class RsProjectSummaryExportModel implements Serializable {

    private String companyName;

    private Integer year;

    /**
     * 项目名
     */
    private String pname;

    /**
     * rd
     */
    private String rdTitle;

    /**
     * 周期
     */
    private String beginAndEnd;

    /**
     * 技术负责人
     */
    private String realName;

    /**
     * 财务负责人
     */
    private String financeName;

    /**
     * 项目成员数
     */
    private String memberCnt;

    /**
     * 设备数
     */
    private String equipmentCnt;

    /**
     * 车间、工艺段
     */
    private String workshop;

    /**
     * 相关专利
     */
    private String patentCnt;

    /**
     * 立项报告
     */
    private String hasReport;

    /**
     * 查新
     */
    private String hasNewReport;

    /**
     * 过程文档数
     */
    private String docCnt;

    /**
     * 备查资料
     */
    private String backupDataTotalCnt;

    private String docTotal;

    private String groupName;

    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getBeginAndEnd() {
        return beginAndEnd;
    }

    public void setBeginAndEnd(String beginAndEnd) {
        this.beginAndEnd = beginAndEnd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public String getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(String memberCnt) {
        this.memberCnt = memberCnt;
    }

    public String getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(String equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(String patentCnt) {
        this.patentCnt = patentCnt;
    }

    public String getHasReport() {
        return hasReport;
    }

    public void setHasReport(String hasReport) {
        this.hasReport = hasReport;
    }

    public String getHasNewReport() {
        return hasNewReport;
    }

    public void setHasNewReport(String hasNewReport) {
        this.hasNewReport = hasNewReport;
    }

    public String getDocCnt() {
        return docCnt;
    }

    public void setDocCnt(String docCnt) {
        this.docCnt = docCnt;
    }

    public String getBackupDataTotalCnt() {
        return backupDataTotalCnt;
    }

    public void setBackupDataTotalCnt(String backupDataTotalCnt) {
        this.backupDataTotalCnt = backupDataTotalCnt;
    }

    public String getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(String docTotal) {
        this.docTotal = docTotal;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
