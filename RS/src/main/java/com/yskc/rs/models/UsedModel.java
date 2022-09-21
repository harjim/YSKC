package com.yskc.rs.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/8/25 8:29
 * description:数据归集提示model
 */
public class UsedModel implements Serializable {

    private Integer id;

    private String rdTitle;

    private BigDecimal rdAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
