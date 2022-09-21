package com.yskc.rs.models.trialprod;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目试制model
 *
 * @author zhangdingfu
 */
public class TrialProdModel {

    private Integer id;
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

    private String unit;
    private String place;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
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
}
