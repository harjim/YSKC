package com.yskc.ms.models.rdfunds;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/4/27 10:56
 * @Description:设备归集费用
 * @author: hsx
 */
public class EquipmentFeesModel extends PageParams implements Serializable {

    private String ecode;

    private String ename;

    private Integer etype;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdDepreciation;

    private String deptName;

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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
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

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdDepreciation() {
        return rdDepreciation;
    }

    public void setRdDepreciation(BigDecimal rdDepreciation) {
        this.rdDepreciation = rdDepreciation;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
