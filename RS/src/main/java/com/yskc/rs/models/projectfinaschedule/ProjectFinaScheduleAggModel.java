package com.yskc.rs.models.projectfinaschedule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectfinaschedule
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-21 09:13
 * @Description: 项目实验试制实际工时归集
 */
public class ProjectFinaScheduleAggModel {

    private List<Integer> types;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date month;

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
