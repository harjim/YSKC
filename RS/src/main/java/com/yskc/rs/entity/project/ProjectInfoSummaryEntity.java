package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/5 17:16
 * @Description:项目信息汇总
 */
@TableName("p_info_summary")
public class ProjectInfoSummaryEntity {

    @TableId
    private Integer id;

    private Date createTime;

    private Date lastUpdateTime;
    private Integer companyId;
    private Integer projectId;
    private Date month;
    private BigDecimal staffRdHour;
    private BigDecimal prodRdHour;
    private BigDecimal materialRaw;
    private BigDecimal materialAuxiliary;
    private BigDecimal materialReserve;
    private BigDecimal trialRaw;
    private BigDecimal trialAuxiliary;
    private BigDecimal trialReserve;
    private BigDecimal repairRaw;
    private BigDecimal repairAuxiliary;
    private BigDecimal repairReserve;
    private BigDecimal yieldAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

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

    public BigDecimal getStaffRdHour() {
        return staffRdHour;
    }

    public void setStaffRdHour(BigDecimal staffRdHour) {
        this.staffRdHour = staffRdHour;
    }

    public BigDecimal getProdRdHour() {
        return prodRdHour;
    }

    public void setProdRdHour(BigDecimal prodRdHour) {
        this.prodRdHour = prodRdHour;
    }

    public BigDecimal getMaterialRaw() {
        return materialRaw;
    }

    public void setMaterialRaw(BigDecimal materialRaw) {
        this.materialRaw = materialRaw;
    }

    public BigDecimal getMaterialAuxiliary() {
        return materialAuxiliary;
    }

    public void setMaterialAuxiliary(BigDecimal materialAuxiliary) {
        this.materialAuxiliary = materialAuxiliary;
    }

    public BigDecimal getMaterialReserve() {
        return materialReserve;
    }

    public void setMaterialReserve(BigDecimal materialReserve) {
        this.materialReserve = materialReserve;
    }

    public BigDecimal getYieldAmount() {
        return yieldAmount;
    }

    public void setYieldAmount(BigDecimal yieldAmount) {
        this.yieldAmount = yieldAmount;
    }

    public static Object getValue(ProjectInfoSummaryEntity entity,String name) throws Exception{
        Method[] m = entity.getClass().getMethods();
        for(int i = 0;i < m.length;i++){
            if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                return m[i].invoke(entity,null);
            }
        }
        return null;

    }

    public BigDecimal getTrialRaw() {
        return trialRaw;
    }

    public void setTrialRaw(BigDecimal trialRaw) {
        this.trialRaw = trialRaw;
    }

    public BigDecimal getTrialAuxiliary() {
        return trialAuxiliary;
    }

    public void setTrialAuxiliary(BigDecimal trialAuxiliary) {
        this.trialAuxiliary = trialAuxiliary;
    }

    public BigDecimal getTrialReserve() {
        return trialReserve;
    }

    public void setTrialReserve(BigDecimal trialReserve) {
        this.trialReserve = trialReserve;
    }

    public BigDecimal getRepairRaw() {
        return repairRaw;
    }

    public void setRepairRaw(BigDecimal repairRaw) {
        this.repairRaw = repairRaw;
    }

    public BigDecimal getRepairAuxiliary() {
        return repairAuxiliary;
    }

    public void setRepairAuxiliary(BigDecimal repairAuxiliary) {
        this.repairAuxiliary = repairAuxiliary;
    }

    public BigDecimal getRepairReserve() {
        return repairReserve;
    }

    public void setRepairReserve(BigDecimal repairReserve) {
        this.repairReserve = repairReserve;
    }
}
