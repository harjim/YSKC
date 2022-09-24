package com.yskc.ms.models.tech;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: 备案导出
 * @author: wjy
 * @create: 2022-09-19 09:04
 **/
public class BeianExportModel extends BeianInfoModel {
    private String deptName;
    private String address;
    private String addressCode;
    private String companyLevel;
    private String projectNo;//项目编号
    private Integer changedCnt;//变更次数
    private String changedDates;//变更时间
    private String change;
    private Integer num;

    private Integer equipmentQuantity;//备案资产数量
    private Integer equipmentCnt;//备案资产项数
    private Date beginDate;//计划开始日期
    private Date endDate;//计划结束日期
    private String date;//计划周期
    private BigDecimal totalAmountTax;//备案金额(含税)
    private BigDecimal powerUsed;//项目用电
    private BigDecimal energyUsed;//项目用能

    private BigDecimal taxAmount;//发票总额
    private BigDecimal taxAmountDetail;//发票完工率

    private BigDecimal amount;//付款总额
    private BigDecimal amountDetail;//付款完工率

    private String accountName;
    private String accountPassword;

    public BigDecimal getDetail(BigDecimal decimal){
        if (decimal!=null&&decimal.compareTo(BigDecimal.ZERO)>0&&
        this.totalAmountTax!=null&&this.totalAmountTax.compareTo(BigDecimal.ZERO)>0){
            return decimal.divide(this.totalAmountTax,4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        }else {
            return BigDecimal.ZERO;
        }
    }

    public String getChangedDates() {
        return changedDates;
    }

    public void setChangedDates(String changedDates) {
        this.changedDates = changedDates;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Integer getChangedCnt() {
        return changedCnt;
    }

    public void setChangedCnt(Integer changedCnt) {
        this.changedCnt = changedCnt;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public Integer getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(Integer equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public Integer getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(Integer equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getTotalAmountTax() {
        return totalAmountTax;
    }

    public void setTotalAmountTax(BigDecimal totalAmountTax) {
        this.totalAmountTax = totalAmountTax;
    }

    public BigDecimal getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(BigDecimal powerUsed) {
        this.powerUsed = powerUsed;
    }

    public BigDecimal getEnergyUsed() {
        return energyUsed;
    }

    public void setEnergyUsed(BigDecimal energyUsed) {
        this.energyUsed = energyUsed;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxAmountDetail() {
        return taxAmountDetail;
    }

    public void setTaxAmountDetail(BigDecimal taxAmountDetail) {
        this.taxAmountDetail = taxAmountDetail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountDetail() {
        return amountDetail;
    }

    public void setAmountDetail(BigDecimal amountDetail) {
        this.amountDetail = amountDetail;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
