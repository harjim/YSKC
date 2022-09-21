package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.enums.CostEnum;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-15 16:06
 * @Description: 能源excel
 */
public class EnergyExcel implements Serializable {

    @Excel(name = "能源名称", order = 0, fieldName = "ename")
    private String ename;
    @Excel(name = "部门", order = 1, fieldName = "deptName")
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "发生日期", dateFormat = "yyyy-MM-dd", order = 3, fieldName = "occDate")
    private Date occDate;

    @Excel(name = "单价", order = 4, fieldName = "unitPrice")
    private BigDecimal unitPrice;

    @Excel(name = "数量", order = 5, fieldName = "quantity")
    private BigDecimal quantity;

    @Excel(name = "金额", order = 6, fieldName = "amount")
    private BigDecimal amount;

    @Excel(name = "单位", order = 7, fieldName = "unit")
    private String unit;

    @Excel(name = "备注", order = 8, fieldName = "remark")
    private String remark;

    @Excel(name = "科目")
    private String fullAccountName;

    private int type;
    private String typeName;

    @Excel(name = "凭证号")
    private String accNumber;

    private BigDecimal totalAmount;


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
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

    public int getType() {
        return type;
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
        if (!StringUtils.isEmpty(fullAccountName) && !fullAccountName.endsWith(Constant.PATH_SEPARATOR)) {
            return fullAccountName + Constant.PATH_SEPARATOR;
        }
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public String getTypeName() {
        if (!StringUtils.hasLength(typeName)) {
            return CostEnum.getCostEnum(type).getTitle();
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
