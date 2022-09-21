package com.yskc.rs.models.hightech;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highTech
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-28 16:03
 * @Description: 查询高品model
 */
public class QueryHighTechIncomeModel extends PageParams {
    private Date month;
    private Integer year;
    private String voucherNo;
    private String productName;
    private String client;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
