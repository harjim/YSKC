package com.yskc.rs.models;

import com.yskc.rs.entity.data.DataAttendanceEntity;

import java.util.Date;
import java.util.List;

/**
 * 批量添加员工考勤
 * @author huronghua
 */
public class BatchAttendanceModel {
    public Date month;
    private List<DataAttendanceEntity> attendances;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<DataAttendanceEntity> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<DataAttendanceEntity> attendances) {
        this.attendances = attendances;
    }
}
