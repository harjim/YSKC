package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: work_record_item
 * @author: cyj
 * @create: 2022-08-13 09:10
 **/
@TableName("work_record_item")
public class WorkItemEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer workRecordId;
    private String itemType;
    private Date begin;
    private Date end;
    private BigDecimal amount;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkRecordId() {
        return workRecordId;
    }

    public void setWorkRecordId(Integer workRecordId) {
        this.workRecordId = workRecordId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
