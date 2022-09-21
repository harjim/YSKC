package com.yskc.rs.models.aggregation;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/8/4 15:09
 * description:材料归集数据model
 */
public class MaterialExportModel extends AggExportModel implements Serializable {


    private String mname;

    private String mcode;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private String unit;

    private String deptName;

    private BigDecimal rdAmount;

    private String billNo;

    private String accNumber;
    private String remark;

    private BigDecimal used;

    private BigDecimal totalAmount;
    private BigDecimal finishAmount;
    private BigDecimal finishQuantity;
    private BigDecimal finishUnitPrice;
    private BigDecimal wasteAmount;
    private BigDecimal wasteQuantity;
    private BigDecimal wasteUnitPrice;

    private BigDecimal totalYield;
    private BigDecimal rdYield;
    private BigDecimal depreciationRatio;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }


    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
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

    public BigDecimal getRdAmount() {
        return rdAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(BigDecimal finishAmount) {
        this.finishAmount = finishAmount;
    }

    public BigDecimal getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(BigDecimal finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public BigDecimal getFinishUnitPrice() {
        return finishUnitPrice;
    }

    public void setFinishUnitPrice(BigDecimal finishUnitPrice) {
        this.finishUnitPrice = finishUnitPrice;
    }

    public BigDecimal getWasteAmount() {
        return wasteAmount;
    }

    public void setWasteAmount(BigDecimal wasteAmount) {
        this.wasteAmount = wasteAmount;
    }

    public BigDecimal getWasteQuantity() {
        return wasteQuantity;
    }

    public void setWasteQuantity(BigDecimal wasteQuantity) {
        this.wasteQuantity = wasteQuantity;
    }

    public BigDecimal getWasteUnitPrice() {
        return wasteUnitPrice;
    }

    public void setWasteUnitPrice(BigDecimal wasteUnitPrice) {
        this.wasteUnitPrice = wasteUnitPrice;
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

    public BigDecimal getDepreciationRatio() {
        return depreciationRatio;
    }

    public void setDepreciationRatio(BigDecimal depreciationRatio) {
        this.depreciationRatio = depreciationRatio;
    }

    /**
     * 研发投入金额
     *
     * @return
     */
    public BigDecimal getPrimordial() {
        if(used == null){
            return null;
        }
        return used.multiply(unitPrice).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 试制比
     *
     * @return
     */
    public BigDecimal getYieldRatio() {
        if(totalYield == null){
            return null;
        }
        if (totalYield.compareTo(BigDecimal.ZERO) == 0 || rdYield.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return rdYield.divide(totalYield, 6, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 损耗量
     *
     * @return
     */
    public BigDecimal getDepreciationQuantity() {
        if(used == null){
            return null;
        }
        return used.multiply(depreciationRatio).setScale(6, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void sum(AggExportModel source) {
        rdAmount = rdAmount.add(((MaterialExportModel)source).rdAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public AggExportModel createSumObj() {
        MaterialExportModel obj = new MaterialExportModel();
        obj.setDateStr("合计");
        obj.rdAmount = BigDecimal.ZERO;
        return obj;
    }
}
