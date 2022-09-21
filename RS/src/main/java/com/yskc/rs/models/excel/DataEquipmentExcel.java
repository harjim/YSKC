package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.rs.enums.EquipmentEnum;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 设备使用列表
 * @Param:
 * @return:
 * @Author: zhangdingfu
 * @date: 2019-07-12
 */
public class DataEquipmentExcel implements Serializable {


    @Excel(name = "设备名称", order = 0, fieldName = "ename")
    private String ename;

    @Excel(name = "资产代码", order = 1, fieldName = "ecode")
    private String ecode;

    @Excel(name = "设备型号", order = 2, fieldName = "emodal")
    private String emodal;

    @Excel(name = "部门", order = 3, fieldName = "deptName")
    private String deptName;

    @Excel(name = "设备类型", order = 4, fieldName = "typeName")
    private String typeName;

    @Excel(name = "月折旧率", order = 12, fieldName = "depreciationRate")
    private BigDecimal depreciationRate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "月份", dateFormat = "yyyy-MM", order = 14, fieldName = "month")
    private Date month;

    @Excel(name = "运行工时", order = 15, fieldName = "workHours")
    private BigDecimal workHours;

    @Excel(name = "月折旧额", order = 16, fieldName = "depreciation")
    private BigDecimal depreciation;
    @Excel(name = "科目")
    private String fullAccountName;

    private Integer etype;

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

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

    public String getTypeName() {
        if (StringUtils.isEmpty(typeName)) {
            if (null == etype) {
                return EquipmentEnum.DEFAULT.getTitle();
            }
            return EquipmentEnum.getByType(etype).getTitle();
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }
}
