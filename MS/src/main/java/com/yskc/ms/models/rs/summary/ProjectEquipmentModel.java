package com.yskc.ms.models.rs.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-23 18:04
 * @Description: 项目设备使用记录
 */
public class ProjectEquipmentModel {

    private Integer id;
    private String equData;
    private Date month;
    private String ecode;
    private BigDecimal workHours;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquData() {
        return equData;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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
}
