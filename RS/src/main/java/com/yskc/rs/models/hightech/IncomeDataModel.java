package com.yskc.rs.models.hightech;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/6/1 18:48
 * @Description:
 */
public class IncomeDataModel implements Serializable {

    private Integer id;

    private Integer highTechId;

    private BigDecimal total;

    private Date bookDate;

    private BigDecimal income;

    private String incomeStr;

    public String getIncomeStr() {
        return incomeStr;
    }

    public void setIncomeStr(String incomeStr) {
        this.incomeStr = incomeStr;
    }

    private String month;//导出时拼接数据

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
