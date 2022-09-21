package com.yskc.rs.models.company;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 09:00
 * @Description: 节假日model
 */
public class CompanyHolidayModel {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;
    private String holidays;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
