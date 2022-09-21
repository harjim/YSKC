package com.yskc.rs.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: wangxing
 * @CreateTime: 2019-10-31 14:31
 * @Description: TypeModel
 */
public class TypeModel implements Serializable {
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;
    private Integer projectId;
    private BigDecimal value;
    private Integer type;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
