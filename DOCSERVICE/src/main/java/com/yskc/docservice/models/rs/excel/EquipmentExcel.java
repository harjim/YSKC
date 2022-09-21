package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.docservice.enums.EquipmentEnum;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-16 10:41
 * @Description: 设备excel操作
 */
public class EquipmentExcel implements Serializable {

    @Excel(name = "设备名称", order = 0, fieldName = "ename")
    private String ename;

    @Excel(name = "资产代码", order = 1, fieldName = "ecode")
    private String ecode;

    @Excel(name = "设备型号", order = 2, fieldName = "emodal")
    private String emodal;

    @Excel(name = "部门", order = 3, fieldName = "fullname")
    private String fullname;

    @Excel(name = "设备类型", order = 4, fieldName = "typeName")
    private String typeName;

    @Excel(name = "原值", order = 6, fieldName = "unitPrice")
    private BigDecimal unitPrice;

    @Excel(name = "单位", order = 7, fieldName = "unit")
    private String unit;

    @Excel(name = "数量", order = 8, fieldName = "quantity")
    private BigDecimal quantity;

    @Excel(name = "使用年限", order = 9, fieldName = "usefullife")
    private Integer usefullife;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "开始计提日期", order = 10, dateFormat = "yyyy-MM-dd", fieldName = "purchaseDate")
    private Date purchaseDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "折旧日期", order = 11, dateFormat = "yyyy-MM-dd", fieldName = "depreciationDate")
    private Date depreciationDate;

    @Excel(name = "月折旧率", order = 12, fieldName = "depreciationRate")
    private BigDecimal depreciationRate;

    @Excel(name = "功率", order = 14, fieldName = "usagePower")
    private BigDecimal usagePower;

    @Excel(name = "类别", order = 15, fieldName = "kinds")
    private String kinds;

    @Excel(name = "备注", order = 16, fieldName = "remark")
    private String remark;
    private String rdDeptName;
    private String workshop;
    private String productLine;
    private String processSection;
    private String rds;
    private String lackMonth;
    private Integer etype;
    private String data;
    private Date invalidated;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getTypeName() {
        if (!StringUtils.hasLength(typeName) && null != etype) {
            return EquipmentEnum.getByType(etype).getTitle();
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getUsefullife() {
        return usefullife;
    }

    public void setUsefullife(Integer usefullife) {
        this.usefullife = usefullife;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public String getLackMonth() {
        return lackMonth;
    }

    public void setLackMonth(String lackMonth) {
        this.lackMonth = lackMonth;
    }

    public Date getInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Date invalidated) {
        this.invalidated = invalidated;
    }
}
