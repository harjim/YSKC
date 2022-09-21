package com.yskc.rs.models.data;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.data
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-14 10:37
 * @Description: 设备使用info
 */
public class DataEquipmentInfoModel {
    private Integer id;
    private String ecode;
    private BigDecimal workHours;
    private BigDecimal remainHours;
    private BigDecimal depreciation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getRemainHours() {
        return remainHours == null ? workHours : remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }
}
