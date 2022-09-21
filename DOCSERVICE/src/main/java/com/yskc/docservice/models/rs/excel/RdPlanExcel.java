package com.yskc.docservice.models.rs.excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/5 10:48
 * @Description:研发人员计划导入
 */
public class RdPlanExcel implements Serializable {

    private String rdTitle;

    private Date month;

    private String ename;

    private String enumber;

    private BigDecimal planTime;

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }
}
