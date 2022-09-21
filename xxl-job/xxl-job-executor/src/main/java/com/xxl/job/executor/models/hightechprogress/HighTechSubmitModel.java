package com.xxl.job.executor.models.hightechprogress;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/5/21 10:46
 * @Description:
 * @author: hsx
 */
public class HighTechSubmitModel implements Serializable {

    private String time;

    private List<Integer> companyIds;

    private Integer range;

    private Date lastTime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<Integer> companyIds) {
        this.companyIds = companyIds;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
