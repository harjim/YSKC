package com.yskc.docservice.models.rs.projectrdequipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.docservice.models.rs.project.RdUsedHourModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdequipment
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:29
 * @Description: 项目设备研发折旧model
 */
public class ProjectRdEquipmentModel implements Serializable {
    private Integer id;
    private Integer projectId;
    private String ename;
    private String ecode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdDepreciation;
    private Integer etype;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainHour;
    private List<RdUsedHourModel> usedList;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal usagePower;
    private BigDecimal powerRate;
    private BigDecimal powerUnitPrice;
    private String effect;
    private String deptName;
    private Date month;

    public ProjectRdEquipmentModel() {
    }

    public ProjectRdEquipmentModel(Integer projectId, String ecode, Date month) {
        this.projectId = projectId;
        this.ecode = ecode;
        this.month = month;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public BigDecimal getPowerUnitPrice() {
        return powerUnitPrice;
    }

    public void setPowerUnitPrice(BigDecimal powerUnitPrice) {
        this.powerUnitPrice = powerUnitPrice;
    }

    public BigDecimal getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(BigDecimal powerRate) {
        this.powerRate = powerRate;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }
    public BigDecimal getRdHourByBit(int bit) {
        if (rdHour != null) {
            return rdHour.setScale(bit, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdDepreciation() {
        return rdDepreciation;
    }

    public void setRdDepreciation(BigDecimal rdDepreciation) {
        this.rdDepreciation = rdDepreciation;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public BigDecimal getRemainHour() {
        return remainHour;
    }

    public void setRemainHour(BigDecimal remainHour) {
        this.remainHour = remainHour;
    }

    public List<RdUsedHourModel> getUsedList() {
        return usedList;
    }

    public void setUsedList(List<RdUsedHourModel> usedList) {
        this.usedList = usedList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
