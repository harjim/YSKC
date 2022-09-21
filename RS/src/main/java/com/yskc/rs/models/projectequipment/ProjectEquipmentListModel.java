package com.yskc.rs.models.projectequipment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 17:3241
 * @Description:设备使用列表
 */
public class ProjectEquipmentListModel implements Serializable {
    private Integer id;
    private Integer equipmentDataId;

    private String ecode;

    private String ename;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private String emodal;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;

    private Integer usefullife;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciationRate;

    private String remainEquData;

    private Integer deptId;

    private Integer rdDeptId;

    private Integer etype;

    private Integer workHours;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public Integer getEquipmentDataId() {
        return equipmentDataId;
    }

    public void setEquipmentDataId(Integer equipmentDataId) {
        this.equipmentDataId = equipmentDataId;
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

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getRemainEquData() {
        return remainEquData;
    }

    public void setRemainEquData(String remainEquData) {
        this.remainEquData = remainEquData;
    }

    public Integer getUsefullife() {
        return usefullife;
    }

    public void setUsefullife(Integer usefullife) {
        this.usefullife = usefullife;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
