package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @Author: hck
 * @DateTime: 2021/3/5 17:16
 * @Description:
 */
@TableName("p_info_summary")
public class ProjectInfoSummary {

    @TableId
    private Integer id;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer companyId;
    private Integer projectId;
    private Date month;
    private BigDecimal staffRdHour;
    private BigDecimal prodRdHour;
    private BigDecimal yieldAmount;
    private BigDecimal materialRaw;
    private BigDecimal materialAuxiliary;
    private BigDecimal materialReserve;
    private BigDecimal trialRaw;
    private BigDecimal trialAuxiliary;
    private BigDecimal trialReserve;
    private BigDecimal repairRaw;
    private BigDecimal repairAuxiliary;
    private BigDecimal repairReserve;


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

    public void resetData(ProjectInfoSummary item) {
        if (null != item.staffRdHour) {
            this.staffRdHour = item.staffRdHour;
        }
        if (null != item.prodRdHour) {
            this.prodRdHour = item.prodRdHour;
        }
        if (null != item.yieldAmount) {
            this.yieldAmount = item.yieldAmount;
        }
        if (null != item.materialRaw) {
            this.materialRaw = item.materialRaw;
        }
        if (null != item.materialAuxiliary) {
            this.materialAuxiliary = item.materialAuxiliary;
        }
        if (null != item.materialReserve) {
            this.materialReserve = item.materialReserve;
        }
        if (null != item.trialRaw) {
            this.trialRaw = item.trialRaw;
        }
        if (null != item.trialAuxiliary) {
            this.trialAuxiliary = item.trialAuxiliary;
        }
        if (null != item.trialReserve) {
            this.trialReserve = item.trialReserve;
        }
        if (null != item.repairRaw) {
            this.repairRaw = item.repairRaw;
        }
        if (null != item.repairAuxiliary) {
            this.repairAuxiliary = item.repairAuxiliary;
        }
        if (null != item.repairReserve) {
            this.repairReserve = item.repairReserve;
        }
    }

    public String getNotNullStr() {
        if (null != this.staffRdHour) {
            return "staffRdHour";
        }
        if (null != this.prodRdHour) {
            return "prodRdHour";
        }
        if (null != this.yieldAmount) {
            return "yieldAmount";
        }
        if (null != this.materialRaw || null != this.materialAuxiliary || null != this.materialReserve ||
                null != this.trialRaw || null != this.trialAuxiliary || null != this.trialReserve ||
                null != this.repairRaw || null != this.repairAuxiliary || null != this.repairReserve) {
            return "material";
        }
        return "";

    }

    public void clear(Set<String> updates, Date now) {
        for (String update : updates) {
            if ("staffRdHour".equals(update)) {
                this.staffRdHour = null;
            }
            if ("prodRdHour".equals(update)) {
                this.prodRdHour = null;
            }
            if ("yieldAmount".equals(update)) {
                this.yieldAmount = null;
            }
            if ("material".equals(update)) {
                this.repairReserve = this.repairAuxiliary = this.repairRaw =
                        this.trialReserve = this.trialAuxiliary = this.trialRaw =
                                this.materialReserve = this.materialAuxiliary = this.materialRaw = null;
            }
        }
        setTime(now);

    }

    public void updateData(ProjectInfoSummary item) {
        if (null == this.staffRdHour) {
            this.staffRdHour = item.staffRdHour;
        }
        if (null == this.prodRdHour) {
            this.prodRdHour = item.prodRdHour;
        }
        if (null == this.yieldAmount) {
            this.yieldAmount = item.yieldAmount;
        }
        if (null == this.materialRaw) {
            this.materialRaw = item.materialRaw;
        }
        if (null == this.materialAuxiliary) {
            this.materialAuxiliary = item.materialAuxiliary;
        }
        if (null == this.materialReserve) {
            this.materialReserve = item.materialReserve;
        }
        if (null == this.trialRaw) {
            this.trialRaw = item.trialRaw;
        }
        if (null == this.trialAuxiliary) {
            this.trialAuxiliary = item.trialAuxiliary;
        }
        if (null == this.trialReserve) {
            this.trialReserve = item.trialReserve;
        }
        if (null == this.repairRaw) {
            this.repairRaw = item.repairRaw;
        }
        if (null == this.repairAuxiliary) {
            this.repairAuxiliary = item.repairAuxiliary;
        }
        if (null == this.repairReserve) {
            this.repairReserve = item.repairReserve;
        }
    }

    public void setTime(Date now) {
        this.createTime = now;
        this.lastUpdateTime = now;
    }
}
