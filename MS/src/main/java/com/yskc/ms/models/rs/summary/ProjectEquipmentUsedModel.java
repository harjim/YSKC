package com.yskc.ms.models.rs.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-24 08:39
 * @Description: 项目设备使用
 */
public class ProjectEquipmentUsedModel {

    private Integer id;

    private String ecode;

    private Date month;

    private String usedEquData;

    private BigDecimal workHours;

    private BigDecimal remainHours;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getUsedEquData() {
        return usedEquData;
    }

    public void setUsedEquData(String usedEquData) {
        this.usedEquData = usedEquData;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }
}
