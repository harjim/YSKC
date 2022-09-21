package com.yskc.ms.models.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ms
 * @description: 跟进记录
 * @author: cyj
 * @create: 2022-05-31 14:26
 **/
public class CustomerTraceModel implements Serializable {
    private Integer id;
    private Integer customerId;
    private String info;
    private Integer type;
    private Integer creatorId;
    private String effectUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEffectUser() {
        return effectUser;
    }

    public void setEffectUser(String effectUser) {
        this.effectUser = effectUser;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }
}
