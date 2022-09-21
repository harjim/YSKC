package com.yskc.docservice.models.rs;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-09 08:58
 * @Description: 时间区间
 */
public class TimeRangeModel {

    private Long onTime;

    private Long offTime;

    public Long getOnTime() {
        return onTime;
    }

    public void setOnTime(Long onTime) {
        this.onTime = onTime;
    }

    public Long getOffTime() {
        return offTime;
    }

    public void setOffTime(Long offTime) {
        this.offTime = offTime;
    }

    public TimeRangeModel(Long onTime, Long offTime) {
        this.onTime = onTime;
        this.offTime = offTime;
    }


    public TimeRangeModel(Date onTime, Date offTime) {
        this.onTime = onTime.getTime();
        this.offTime = offTime.getTime();
    }

    public TimeRangeModel() {
    }
}
