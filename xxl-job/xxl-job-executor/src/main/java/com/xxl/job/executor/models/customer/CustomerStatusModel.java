package com.xxl.job.executor.models.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: xxl-job
 * @description: 客户状态
 * @author: cyj
 * @create: 2022-06-02 09:27
 **/
public class CustomerStatusModel implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;
    private Integer id;
    private Integer owerId;
    private Integer deptId;
    private Integer status;

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
