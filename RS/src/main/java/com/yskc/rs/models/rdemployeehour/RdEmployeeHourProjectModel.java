package com.yskc.rs.models.rdemployeehour;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdemployeehour
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-08 09:27
 * @Description: 研发人员投入工时项目信息
 */
public class RdEmployeeHourProjectModel {

    private String enumber;
    private String rds;
    private Date entryDate;
    private BigDecimal rdHour;

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }
}
