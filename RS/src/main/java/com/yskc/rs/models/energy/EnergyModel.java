package com.yskc.rs.models.energy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.utils.DateUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.energy
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 08:09
 * @Description: 能源modal
 */
public class EnergyModel implements Serializable {

    private Integer id;

    @NotBlank(message = "能源名称不能为空")
    private String ename;

    private Integer type;

    @NotNull(message = "发生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date occDate;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    @Size(max = 10, message = "单位不能超过10个字")
    private String unit;

    @Size(max = 200, message = "备注不能超过200个字")
    private String remark;

    private String deptName;

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    private String fullAccountName;

    private Integer accountTitleId;

    private String accNumber;

    private BigDecimal totalAmount;

    public Date getOccDateMonth() {
        return DateUtil.getMonthFirstDay(occDate);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setOccDate(Date occDate) {
        this.occDate = occDate;
    }

    public Date getOccDate() {
        return occDate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
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
}
