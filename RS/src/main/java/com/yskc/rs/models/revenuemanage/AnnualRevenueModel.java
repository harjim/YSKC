package com.yskc.rs.models.revenuemanage;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/1/14 15:46
 * @Description:按年份合并年度营收数据model
 * @author: hsx
 */
public class AnnualRevenueModel implements Serializable {

    //年份
    private Integer year;

    //一月数据
    private BigDecimal feb;

    //二月数据
    private BigDecimal mar;

    //三月数据
    private BigDecimal jan;

    //四月数据
    private BigDecimal apr;

    //五月数据
    private BigDecimal may;

    //六月数据
    private BigDecimal jun;

    //七月数据
    private BigDecimal jul;

    //八月数据
    private BigDecimal aug;

    //九月数据
    private BigDecimal sept;

    //十月数据
    private BigDecimal oct;

    //十一月数据
    private BigDecimal nov;

    //十二月数据
    private BigDecimal dec;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getFeb() {
        return feb;
    }

    public void setFeb(BigDecimal feb) {
        this.feb = feb;
    }

    public BigDecimal getMar() {
        return mar;
    }

    public void setMar(BigDecimal mar) {
        this.mar = mar;
    }

    public BigDecimal getJan() {
        return jan;
    }

    public void setJan(BigDecimal jan) {
        this.jan = jan;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getMay() {
        return may;
    }

    public void setMay(BigDecimal may) {
        this.may = may;
    }

    public BigDecimal getJun() {
        return jun;
    }

    public void setJun(BigDecimal jun) {
        this.jun = jun;
    }

    public BigDecimal getJul() {
        return jul;
    }

    public void setJul(BigDecimal jul) {
        this.jul = jul;
    }

    public BigDecimal getAug() {
        return aug;
    }

    public void setAug(BigDecimal aug) {
        this.aug = aug;
    }

    public BigDecimal getSept() {
        return sept;
    }

    public void setSept(BigDecimal sept) {
        this.sept = sept;
    }

    public BigDecimal getOct() {
        return oct;
    }

    public void setOct(BigDecimal oct) {
        this.oct = oct;
    }

    public BigDecimal getNov() {
        return nov;
    }

    public void setNov(BigDecimal nov) {
        this.nov = nov;
    }

    public BigDecimal getDec() {
        return dec;
    }

    public void setDec(BigDecimal dec) {
        this.dec = dec;
    }
}
