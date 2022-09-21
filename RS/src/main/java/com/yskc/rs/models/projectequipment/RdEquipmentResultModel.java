package com.yskc.rs.models.projectequipment;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/5/28 14:29
 * description:
 */
public class RdEquipmentResultModel implements Serializable {
    /**
     * 序号
     */
    private  Integer num;
    /**
     * 资产编码
     */
    private String ecode;
    /**
     *作用
     */
    private String effect;
    /**
     * 设备名称
     */
    private String ename;
    /**
     * 设备型号
     */
    private String emodal;
    /**
     * 月运行小时
     */
    private BigDecimal monthHour;
    /**
     * 年运行小时
     */
    private BigDecimal yearHour;

    public BigDecimal getMonthHour() {
        return monthHour;
    }

    public void setMonthHour(BigDecimal monthHour) {
        this.monthHour = monthHour;
    }

    public BigDecimal getYearHour() {
        return yearHour;
    }

    public void setYearHour(BigDecimal yearHour) {
        this.yearHour = yearHour;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
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
}
