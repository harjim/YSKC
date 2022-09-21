package com.yskc.ms.models.company;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/12/5 10:20
 * description:
 */
public class FinanceInfoModel implements Serializable {

    private Integer companyId;

    private Integer year;

    private String key;

    private BigDecimal value;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
