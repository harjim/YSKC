package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.math.BigDecimal;

/**
 * @program: ms
 * @description: work_record
 * @author: cyj
 * @create: 2022-08-13 09:07
 **/
@TableName("work_record")
public class WorkRecordEntity extends MsBaseEntity {

    @TableId
    private Integer id;
    private Integer customerId;
    private String serviceNo;
    private Integer ownerId;
    private Integer itemCnt;
    private Integer deptId;
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(Integer itemCnt) {
        this.itemCnt = itemCnt;
    }
}
