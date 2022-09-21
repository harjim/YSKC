package com.yskc.rs.models.hightechprogress;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: rs
 * @description: 高新进度统计
 * @author: cyj
 * @create: 2022-05-27 15:06
 **/
public class HighTechAmountModel implements Serializable {
    private Integer amount;
    private Integer deliverType;
    private Date month;
    private Integer status;
    private Integer node;

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(Integer deliverType) {
        this.deliverType = deliverType;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
