package com.yskc.ms.models.rdfunds;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/4/27 15:42
 * @Description:设备用电归集费用model
 * @author: hsx
 */
public class EquipmentPowerFeesModel implements Serializable {

    private String ecode;

    private String ename;

    private Integer etype;

    private String deptName;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal usagePower;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal powerUnitPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal powerRate;

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
