package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-03 14:27
 * @Description: 设备清单excel
 */
public class InitEquipmentExcel implements Serializable {
    @Excel(name = "资产代码", order = 0, fieldName = "ecode")
    private String ecode;
    @Excel(name = "设备名称", order = 1, fieldName = "ename")
    private String ename;

    @Excel(name = "所属部门", order = 2, fieldName = "deptName")
    private String deptName;

    @Excel(name = "设备型号", order = 3, fieldName = "emodal")
    private String emodal;

    @Excel(name = "原值", order = 4, fieldName = "unitPrice")
    private BigDecimal unitPrice;

    @Excel(name = "设备类型", order = 5, fieldName = "typeName")
    private String typeName;

    @Excel(name = "项目作用", order = 6, fieldName = "effect")
    private String effect;

    @Excel(name = "进入日期")
    private Date entryDate;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
