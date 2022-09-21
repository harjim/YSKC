package com.yskc.ms.models.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ms
 * @description: 客户列表跟进
 * @author: cyj
 * @create: 2022-05-31 13:43
 **/
public class QueryFollowModel implements Serializable {
    private Integer customerId;
    private Integer status;
    private String info;
    private Integer creatorId;
    private Date createTime;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
