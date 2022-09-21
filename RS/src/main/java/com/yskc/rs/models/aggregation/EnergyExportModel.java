package com.yskc.rs.models.aggregation;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-04 15:12
 * @Description: 能源导出model
 */
public class EnergyExportModel extends AggExportModel {

    private String ename;
    private String accNumber;
    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private String unit;

    private String deptName;

    private BigDecimal amount;

    private BigDecimal totalHour;

    private BigDecimal rdHour;

    private BigDecimal rdAmount;

    private BigDecimal totalYield;

    private BigDecimal rdYield;

    private BigDecimal totalAmount;


    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getRdQuantity() {
        if (rdAmount == null || unitPrice.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return rdAmount.divide(unitPrice, 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getYieldRatio() {
        if (totalYield == null) {
            return null;
        }
        if (totalYield.compareTo(BigDecimal.ZERO) != 0 && rdYield.compareTo(BigDecimal.ZERO) != 0) {
            return rdYield.divide(totalYield, 6, BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO.setScale(6);
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    @Override
    public void sum(AggExportModel source) {
        rdAmount = rdAmount.add(((EnergyExportModel) source).rdAmount);
    }

    @Override
    public AggExportModel createSumObj() {
        EnergyExportModel obj = new EnergyExportModel();
        obj.setDateStr("合计");
        obj.rdAmount = BigDecimal.ZERO;
        return obj;
    }
}
