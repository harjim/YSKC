package com.yskc.ms.entity.rs;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.config.Constant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("p_trialProd")
public class TrialProdEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer projectId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trialDate;
    private Integer planPO;
    private Integer actualPO;
    private String pos;
    private String trialGroup;
    private BigDecimal auxMaterial;
    private BigDecimal fuel;
    private BigDecimal gas;
    private BigDecimal mainMaterial;
    private BigDecimal power;
    private BigDecimal spare;

    private Integer creatorId;
    private Date createTime;
    private Integer lastUpdatorId;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private String unit;
    private String place;
    private Date startTime;
    private Date endTime;


    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getTrialGroup() {
        return trialGroup;
    }

    public void setTrialGroup(String trialGroup) {
        this.trialGroup = trialGroup;
    }

    public BigDecimal getAuxMaterial() {
        return auxMaterial;
    }

    public void setAuxMaterial(BigDecimal auxMaterial) {
        this.auxMaterial = auxMaterial;
    }

    public BigDecimal getFuel() {
        return fuel;
    }

    public void setFuel(BigDecimal fuel) {
        this.fuel = fuel;
    }

    public BigDecimal getGas() {
        return gas;
    }

    public void setGas(BigDecimal gas) {
        this.gas = gas;
    }

    public BigDecimal getMainMaterial() {
        return mainMaterial;
    }

    public void setMainMaterial(BigDecimal mainMaterial) {
        this.mainMaterial = mainMaterial;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getSpare() {
        return spare;
    }

    public void setSpare(BigDecimal spare) {
        this.spare = spare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public Integer getPlanPO() {
        return planPO;
    }

    public void setPlanPO(Integer planPO) {
        this.planPO = planPO;
    }

    public Integer getActualPO() {
        return actualPO;
    }

    public void setActualPO(Integer actualPO) {
        this.actualPO = actualPO;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public void setZeroSecond() {
        if (this.endTime != null) {
            this.endTime = DateUtil.date(this.endTime.getTime() / Constant.SECOND * Constant.SECOND);
        }
        if (this.startTime != null) {
            this.startTime = DateUtil.date(this.startTime.getTime() / Constant.SECOND * Constant.SECOND);
        }
    }
}
