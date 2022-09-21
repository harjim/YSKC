package com.yskc.rs.models.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-12 08:24:37
 */
public class DataEquipmentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size(max = 100, message = "资产代码不能超过100个字")
    private String ecode;


    @Size(max = 200, message = "设备名称不能超过200个字")
    private String ename;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private String emodal;

    private Integer etype;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciationRate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;
    private String fullAccountName;
    private Integer accountTitleId;
    private String deptName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
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

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
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

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
