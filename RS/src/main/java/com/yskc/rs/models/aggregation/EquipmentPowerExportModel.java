package com.yskc.rs.models.aggregation;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.enums.EquipmentEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/8/4 16:33
 * description:
 */
public class EquipmentPowerExportModel extends AggExportModel implements Serializable {


    private String ecode;

    private String ename;

    private String emodal;

    private Integer etype;

    private String deptName;

    private BigDecimal rdHour;


    private BigDecimal powerRate;

    private BigDecimal powerUnitPrice;

    private BigDecimal usagePower;

    private String typeName;

    private String monthStr;

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

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getRdHour() {
        if (rdHour != null) {
            return rdHour.setScale(1, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(BigDecimal powerRate) {
        this.powerRate = powerRate;
    }

    public BigDecimal getPowerUnitPrice() {
        return powerUnitPrice;
    }

    public void setPowerUnitPrice(BigDecimal powerUnitPrice) {
        this.powerUnitPrice = powerUnitPrice;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
    }

    public String getTypeName() {
        if (etype == null) {
            return null;
        }
        return EquipmentEnum.getByType(etype).getTitle();
    }

    public String getMonthStr() {
        if (monthStr != null) {
            return monthStr;
        }
        return DateUtil.format(this.getDate(), DateUtil.DEFAULT_YYMM_FORMAT);
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    @Override
    public void sum(AggExportModel source) {
        powerRate = powerRate.add(((EquipmentPowerExportModel) source).powerRate);
    }

    @Override
    public AggExportModel createSumObj() {
        EquipmentPowerExportModel obj = new EquipmentPowerExportModel();
        obj.monthStr = "合计";
        obj.powerRate = BigDecimal.ZERO;
        return obj;
    }
}
