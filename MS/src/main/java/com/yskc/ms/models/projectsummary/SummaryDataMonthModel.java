package com.yskc.ms.models.projectsummary;

import java.math.BigDecimal;

/**
 * @program: ms
 * @description: 据月份，项目获取费用汇总
 * @author: cyj
 * @create: 2022-04-26 09:34
 **/
public class SummaryDataMonthModel {
    //费用类型 0：当月费用 1：月份累计
    private Integer type;
    /*{ value: 100, title: '人员费用', type: 10000 }, // 10000+10100
    { value: 300, title: '设备折旧', type: 30000 }, // 30000+30100
    { value: 200, title: '研发材料', type: 20000 }, // type/100 = 200
    { value: 203, title: '中间试制', type: 20300 }, // type/100 = 203
    { value: 206, title: '修理费用', type: 20600 }, // type/100 = 206
    { value: 207, title: '样机费用', type: 20700 }, // 20700
    { value: 201, title: '动力损耗', type: 20100 }, // type/100 = 201
    { value: 202, title: '燃料损耗', type: 20200 }, // type/100 = 202
    { value: 205, title: '检测费用', type: 20500 }, // 20500
    { value: 500, title: '设计费用', type: 50000 }, // 50000
    { value: 604, title: '差旅费用', type: 60400 }, // 60400
    { value: 400, title: '摊销费用', type: 40000 }, // type/1000 = 40
    { value: 699, title: '其他费用', type: 69900 } // 60000+60100+60200+60300+69900*/

    private BigDecimal k10000;
    private BigDecimal k30000;
    private BigDecimal k20000;
    private BigDecimal k20300;
    private BigDecimal k20600;
    private BigDecimal k20700;
    private BigDecimal k20100;
    private BigDecimal k20200;
    private BigDecimal k20500;
    private BigDecimal k50000;
    private BigDecimal k60400;
    private BigDecimal k40000;
    private BigDecimal k69900;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getK10000() {
        return k10000;
    }

    public void setK10000(BigDecimal k10000) {
        this.k10000 = k10000;
    }

    public BigDecimal getK30000() {
        return k30000;
    }

    public void setK30000(BigDecimal k30000) {
        this.k30000 = k30000;
    }

    public BigDecimal getK20000() {
        return k20000;
    }

    public void setK20000(BigDecimal k20000) {
        this.k20000 = k20000;
    }

    public BigDecimal getK20300() {
        return k20300;
    }

    public void setK20300(BigDecimal k20300) {
        this.k20300 = k20300;
    }

    public BigDecimal getK20600() {
        return k20600;
    }

    public void setK20600(BigDecimal k20600) {
        this.k20600 = k20600;
    }

    public BigDecimal getK20700() {
        return k20700;
    }

    public void setK20700(BigDecimal k20700) {
        this.k20700 = k20700;
    }

    public BigDecimal getK20100() {
        return k20100;
    }

    public void setK20100(BigDecimal k20100) {
        this.k20100 = k20100;
    }

    public BigDecimal getK20200() {
        return k20200;
    }

    public void setK20200(BigDecimal k20200) {
        this.k20200 = k20200;
    }

    public BigDecimal getK20500() {
        return k20500;
    }

    public void setK20500(BigDecimal k20500) {
        this.k20500 = k20500;
    }

    public BigDecimal getK50000() {
        return k50000;
    }

    public void setK50000(BigDecimal k50000) {
        this.k50000 = k50000;
    }

    public BigDecimal getK60400() {
        return k60400;
    }

    public void setK60400(BigDecimal k60400) {
        this.k60400 = k60400;
    }

    public BigDecimal getK40000() {
        return k40000;
    }

    public void setK40000(BigDecimal k40000) {
        this.k40000 = k40000;
    }

    public BigDecimal getK69900() {
        return k69900;
    }

    public void setK69900(BigDecimal k69900) {
        this.k69900 = k69900;
    }
}
