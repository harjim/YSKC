package com.xxl.job.executor.models.attendance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.attendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-29 08:08
 * @Description: 统计考勤
 */
public class TotalAttendanceModel {
    private Integer companyId;
    private String enumber;
    private BigDecimal planTime;
    private BigDecimal rdHour;
    private Date month;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
