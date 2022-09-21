package com.yskc.rs.models.rdhourconfig;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdhourconfig
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-09 09:13
 * @Description: 研发工时配置model
 */
public class RdHourConfigInfoModel {

    private BigDecimal totalHour;

    private BigDecimal dayHour;

    private Integer startDay;

    private BigDecimal defaultTotal;

    /**
     * 通过计算获取,若不存在，则该段无效。
     */
    private Integer endDay;

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getDayHour() {
        return dayHour;
    }

    public void setDayHour(BigDecimal dayHour) {
        this.dayHour = dayHour;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public BigDecimal getDefaultTotal() {
        return defaultTotal;
    }

    public void setDefaultTotal(BigDecimal defaultTotal) {
        this.defaultTotal = defaultTotal;
    }

    public void resetHour() {
        if(null == defaultTotal){
            defaultTotal = totalHour;
        }
        totalHour = defaultTotal;
    }
}
