package com.yskc.rs.models.company;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/7/15 11:12
 * @Description:首页项目统计model
 */
public class CountProjectModel implements Serializable {

    private Integer projectCnt;//原子项目和大项目立项总数

    private String projectCntPercent;

    private Integer projectTrend;//-1 下降 0 相同 1上升

    private Integer childCnt;//子项目立项总数

    private Integer rdEmployeeNum;//研发人员总数

    private String rdEmployeePercent;

    private Integer rdEmployeeTrend;

    private Integer rdEquipmentNum;//研发设备总数

    private String rdEquipmentPercent;

    private Integer rdEquipmentTrend;

    private Integer developmentNum;//建设事项统计

    public CountProjectModel() {
    }

    public CountProjectModel(Integer projectCnt, String projectCntPercent, Integer projectTrend, Integer childCnt, Integer rdEmployeeNum, String rdEmployeePercent, Integer rdEmployeeTrend, Integer rdEquipmentNum, String rdEquipmentPercent, Integer rdEquipmentTrend, Integer developmentNum) {
        this.projectCnt = projectCnt;
        this.projectCntPercent = projectCntPercent;
        this.projectTrend = projectTrend;
        this.childCnt = childCnt;
        this.rdEmployeeNum = rdEmployeeNum;
        this.rdEmployeePercent = rdEmployeePercent;
        this.rdEmployeeTrend = rdEmployeeTrend;
        this.rdEquipmentNum = rdEquipmentNum;
        this.rdEquipmentPercent = rdEquipmentPercent;
        this.rdEquipmentTrend = rdEquipmentTrend;
        this.developmentNum = developmentNum;
    }

    public Integer getProjectTrend() {
        return projectTrend;
    }

    public void setProjectTrend(Integer projectTrend) {
        this.projectTrend = projectTrend;
    }

    public Integer getRdEmployeeTrend() {
        return rdEmployeeTrend;
    }

    public void setRdEmployeeTrend(Integer rdEmployeeTrend) {
        this.rdEmployeeTrend = rdEmployeeTrend;
    }

    public Integer getRdEquipmentTrend() {
        return rdEquipmentTrend;
    }

    public void setRdEquipmentTrend(Integer rdEquipmentTrend) {
        this.rdEquipmentTrend = rdEquipmentTrend;
    }

    public Integer getProjectCnt() {
        return projectCnt;
    }

    public void setProjectCnt(Integer projectCnt) {
        this.projectCnt = projectCnt;
    }

    public String getProjectCntPercent() {
        return projectCntPercent;
    }

    public void setProjectCntPercent(String projectCntPercent) {
        this.projectCntPercent = projectCntPercent;
    }

    public Integer getChildCnt() {
        return childCnt;
    }

    public void setChildCnt(Integer childCnt) {
        this.childCnt = childCnt;
    }

    public Integer getRdEmployeeNum() {
        return rdEmployeeNum;
    }

    public void setRdEmployeeNum(Integer rdEmployeeNum) {
        this.rdEmployeeNum = rdEmployeeNum;
    }

    public String getRdEmployeePercent() {
        return rdEmployeePercent;
    }

    public void setRdEmployeePercent(String rdEmployeePercent) {
        this.rdEmployeePercent = rdEmployeePercent;
    }

    public Integer getRdEquipmentNum() {
        return rdEquipmentNum;
    }

    public void setRdEquipmentNum(Integer rdEquipmentNum) {
        this.rdEquipmentNum = rdEquipmentNum;
    }

    public String getRdEquipmentPercent() {
        return rdEquipmentPercent;
    }

    public void setRdEquipmentPercent(String rdEquipmentPercent) {
        this.rdEquipmentPercent = rdEquipmentPercent;
    }

    public Integer getDevelopmentNum() {
        return developmentNum;
    }

    public void setDevelopmentNum(Integer developmentNum) {
        this.developmentNum = developmentNum;
    }
}
