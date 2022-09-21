package com.yskc.rs.models.projectequipment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-24 10:10
 * @Description: 设备汇总model
 */
public class EquipmentSumModel implements Serializable {

    private Integer projectId;
    private Date month;
    private BigDecimal prod;
    private BigDecimal lab;

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

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }
}
