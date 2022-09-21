package com.yskc.rs.models.projectrdequipment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/5/23 16:00
 * @Description:
 * @author: hsx
 */
public class HighTechProjectRdEquipmentModel implements Serializable {

    private Integer id;

    /**
     * 固定资产编码
     */
    private String ecode;

    /**
     * 固定资产名称
     */
    private String ename;

    /**
     * 规格型号
     */
    private String emodal;

    /**
     * 本期实提折扣额(月折旧)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;

    /**
     * 项目编号
     */
    private String rdTitle;

    /**
     * 总工时(运行工时)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;

    /**
     * 研发工时
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;

    /**
     * 研发折旧
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdDepreciation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
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
}
