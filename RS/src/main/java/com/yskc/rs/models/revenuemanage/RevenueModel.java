package com.yskc.rs.models.revenuemanage;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/1/14 8:31
 * @Description:
 * @author: hsx
 */
public class RevenueModel implements Serializable {

    private Integer id;

    private Integer companyId;

    private Integer year;

    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date month;

    private BigDecimal revenue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

}
