package com.yskc.rs.models.projectattendance;

import com.yskc.rs.models.rdhourconfig.RdHourConfigInfoModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-13 17:55
 * @Description: 批量刷新考勤/使用记录
 */
public class RefreshAttendance {
    private Integer projectId;

    private Date month;

    private List<Integer> ids;

    private BigDecimal dayHour;

    private Date startDay;

    private Boolean skipHoliday;

    private Integer type;

    private Boolean noRepeat;

    private List<RdHourConfigInfoModel> configs;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public BigDecimal getDayHour() {
        return dayHour;
    }

    public void setDayHour(BigDecimal dayHour) {
        this.dayHour = dayHour;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Boolean getSkipHoliday() {
        return skipHoliday;
    }

    public void setSkipHoliday(Boolean skipHoliday) {
        this.skipHoliday = skipHoliday;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<RdHourConfigInfoModel> getConfigs() {
        return configs;
    }

    public void setConfigs(List<RdHourConfigInfoModel> configs) {
        this.configs = configs;
    }

    public Boolean getNoRepeat() {
        return noRepeat != null ? noRepeat : false;
    }

    public void setNoRepeat(Boolean noRepeat) {
        this.noRepeat = noRepeat;
    }
}
