package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.math.BigDecimal;

/**
 * @program: ms
 * @description: checkInst_price
 * @author: cyj
 * @create: 2022-08-09 10:05
 **/
@TableName("checkInst_price")
public class CheckInstPriceEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer checkInstId;
    private Integer days;
    private BigDecimal amount;
    private Integer checkType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckInstId() {
        return checkInstId;
    }

    public void setCheckInstId(Integer checkInstId) {
        this.checkInstId = checkInstId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }
}
