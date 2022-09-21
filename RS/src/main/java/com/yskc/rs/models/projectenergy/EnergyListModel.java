package com.yskc.rs.models.projectenergy;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-28 16:32
 * @Description: 能源损耗列表
 */
public class EnergyListModel implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    @NotBlank(message = "能源名称不能为空")
    private String ename;
    /**
     *
     */
    @NotNull(message = "能源类型不能为空")
    private Integer type;

    @NotNull(message = "发生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date occDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    @NotNull(message = "单位不能为空")
    private String unit;
    private String remark;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

    private String deptName;

    private String accNumber;

    private BigDecimal remainAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getOccDate() {
        return occDate;
    }

    public void setOccDate(Date occDate) {
        this.occDate = occDate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }
}
