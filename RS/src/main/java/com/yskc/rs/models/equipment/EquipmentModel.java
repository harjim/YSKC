package com.yskc.rs.models.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.equipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 11:21
 * @Description: 设备modal
 */
public class EquipmentModel implements Serializable {
    private Integer id;

    @NotNull(message = "设备名称不能为空")
    @NotBlank(message = "设备名称不能为空")
    @Size(max = 200, message = "设备名称不能超过200个字")
    private String ename;

    @NotNull(message = "资产代码不能为空")
    @NotBlank(message = "资产代码不能为空")
    @Size(max = 100, message = "资产代码不能超过100个字")
    private String ecode;

    @Size(max = 100, message = "设备型号不能超过100个字")
    private String emodal;


    @NotNull(message = "原值不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;

    @Size(max = 10, message = "单位不能超过10个字")
    private String unit;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;

    private Integer usefullife;

    @Size(max = 300, message = "备注不能超过300个字")
    private String remark;

    private Date purchaseDate;

    private Date depreciationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciationRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal usagePower;

    private Integer etype;

    @Size(max = 100, message = "类别不能超过100个字")
    private String kinds;
    private Integer deptId;
    private String deptName;
    private String fullname;
    private String workshop;
    private String productLine;
    private String processSection;
    private String data;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invalidated;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Date getInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Date invalidated) {
        this.invalidated = invalidated;
    }
}
