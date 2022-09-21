package com.yskc.rs.models.ratio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.ratio
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-12 14:14
 * @Description: 设备研发比例model
 */
public class EquipmentRatioModel implements Serializable {

    private Integer projectId;

    private Date month;

    private BigDecimal lab;

    private BigDecimal prod;

    private Boolean allReset;

    private Integer firstDay;

    private Integer maxHour;

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

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public Boolean getAllReset() {
        return allReset;
    }

    public void setAllReset(Boolean allReset) {
        this.allReset = allReset;
    }

    public Integer getFirstDay() {
        if (firstDay == null) {
            firstDay = 1;
        }
        return firstDay;
    }

    public void setFirstDay(Integer firstDay) {
        this.firstDay = firstDay;
    }

    public Integer getMaxHour() {
        if (maxHour == null) {
            maxHour = 24;
        }
        return maxHour;
    }

    public void setMaxHour(Integer maxHour) {
        this.maxHour = maxHour;
    }
}
