package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/22 14:35
 * @Description: 项目立项详情
 */
@TableName("rsProject_summary")
public class RsProjectSummary {
    @TableId
    private Integer id;

    private Integer year;

    private Integer projectId;

    private Integer companyId;

    private Integer memberCnt;//项目成员数

    private Integer equipmentCnt;//设备

    private Integer patentCnt;//专利数

    private Boolean hasReport;//是否有立项报告

    private Integer reportStatus;//立项审核状态

    private Boolean hasNewReport;//是否有查新报告

    private Integer newReportStatus;//查新报告状态

    private Integer docTotal;//文档数（已编写数）

    private Integer docSubmitCnt;//提交文档数

    private Integer docDoneCnt;//通过文档数

    private Date createTime;

    private Date updateTime;
    private Integer rdTitle;
    private Integer backupDataTotalCnt;//备查资料审核通过数，跨年项目需区分年度总结报告及项目验收报告(通过数: (通过4个，即为完成)不包含人员折旧，项目编制，辅助帐)


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Integer getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(Integer rdTitle) {
        this.rdTitle = rdTitle;
    }
}
