package com.yskc.rs.models.employeePlan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employeePlan
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-06 09:24
 * @Description: 研发人员计划工时汇总
 */
public class RdEmployeePlanTotalModel {
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;
    private String enumber;
    private String ename;
    private String etype;
    private BigDecimal total;
    //分项目拿工时
    private List<Map<String, Object>> usedList;

    public List<Map<String, Object>> getUsedList() {
        return usedList;
    }

    public void setUsedList(List<Map<String, Object>> usedList) {
        this.usedList = usedList;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
