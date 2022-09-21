package com.yskc.rs.models.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/11 16:30
 * @Description:
 */
public class AttendanceModel implements Serializable {

    private Integer projectId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")

    private String date;

    private String enumber;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }
}
