package com.yskc.ms.models.rdfunds;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/rdfunds
 * @Author: hm
 * @CreateTime: 2022/8/31
 * @Description: 所有类型 按type分 格式‘c’+type
 */
public class RdFundsAllModel implements Serializable {
    private Integer id;
    private String pname;
    private String rdTitle;
    private Date beginDate;
    private Date endDate;
    private Date month;
    /* 费用类型对应字段 */
    private BigDecimal c10000;
    private BigDecimal c20000;
    private BigDecimal c20100;
    private BigDecimal c20200;
    private BigDecimal c20300;
    private BigDecimal c20500;
    private BigDecimal c20600;
    private BigDecimal c20700;
    private BigDecimal c30000;
    private BigDecimal c40000;
    private BigDecimal c50000;
    private BigDecimal c60400;
    private BigDecimal c69900;
    /* 合计 */
    private BigDecimal totalAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getC10000() {
        return c10000;
    }

    public void setC10000(BigDecimal c10000) {
        this.c10000 = c10000;
    }

    public BigDecimal getC20000() {
        return c20000;
    }

    public void setC20000(BigDecimal c20000) {
        this.c20000 = c20000;
    }

    public BigDecimal getC20100() {
        return c20100;
    }

    public void setC20100(BigDecimal c20100) {
        this.c20100 = c20100;
    }

    public BigDecimal getC20200() {
        return c20200;
    }

    public void setC20200(BigDecimal c20200) {
        this.c20200 = c20200;
    }

    public BigDecimal getC20300() {
        return c20300;
    }

    public void setC20300(BigDecimal c20300) {
        this.c20300 = c20300;
    }

    public BigDecimal getC20500() {
        return c20500;
    }

    public void setC20500(BigDecimal c20500) {
        this.c20500 = c20500;
    }

    public BigDecimal getC20600() {
        return c20600;
    }

    public void setC20600(BigDecimal c20600) {
        this.c20600 = c20600;
    }

    public BigDecimal getC20700() {
        return c20700;
    }

    public void setC20700(BigDecimal c20700) {
        this.c20700 = c20700;
    }

    public BigDecimal getC30000() {
        return c30000;
    }

    public void setC30000(BigDecimal c30000) {
        this.c30000 = c30000;
    }

    public BigDecimal getC40000() {
        return c40000;
    }

    public void setC40000(BigDecimal c40000) {
        this.c40000 = c40000;
    }

    public BigDecimal getC50000() {
        return c50000;
    }

    public void setC50000(BigDecimal c50000) {
        this.c50000 = c50000;
    }

    public BigDecimal getC60400() {
        return c60400;
    }

    public void setC60400(BigDecimal c60400) {
        this.c60400 = c60400;
    }

    public BigDecimal getC69900() {
        return c69900;
    }

    public void setC69900(BigDecimal c69900) {
        this.c69900 = c69900;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
