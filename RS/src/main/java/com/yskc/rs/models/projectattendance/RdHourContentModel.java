package com.yskc.rs.models.projectattendance;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-30 08:36
 * @Description: 工时，内容
 */
public class RdHourContentModel {

    private BigDecimal workHour;

    private String content;

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RdHourContentModel() {
    }

    public RdHourContentModel(BigDecimal workHour, String content) {
        this.workHour = workHour;
        this.content = content;
    }
}
