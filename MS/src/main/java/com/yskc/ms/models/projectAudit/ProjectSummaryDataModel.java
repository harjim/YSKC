package com.yskc.ms.models.projectAudit;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/11/25 10:14
 * description:项目审核数据
 */
public class ProjectSummaryDataModel implements Serializable {

    private Integer rdCount;//立项数

    private Integer rdEmployeeCount;//研发人数

    private Integer rdEquipmentCount;//研发设备数

    private Integer rdDeptLevel;//研发架构

    private Integer docFileCount;//过程文件数

    private Integer reportNum;//立项报告数

    private Integer patentNum;//申请专利数

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
    }

    public Integer getRdEquipmentCount() {
        return rdEquipmentCount;
    }

    public void setRdEquipmentCount(Integer rdEquipmentCount) {
        this.rdEquipmentCount = rdEquipmentCount;
    }

    public Integer getRdDeptLevel() {
        return rdDeptLevel;
    }

    public void setRdDeptLevel(Integer rdDeptLevel) {
        this.rdDeptLevel = rdDeptLevel;
    }

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Integer getPatentNum() {
        return patentNum;
    }

    public void setPatentNum(Integer patentNum) {
        this.patentNum = patentNum;
    }
}
