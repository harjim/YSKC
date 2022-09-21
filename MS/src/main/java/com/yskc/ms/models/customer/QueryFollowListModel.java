package com.yskc.ms.models.customer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description: 批量跟进
 * @author: cyj
 * @create: 2022-06-08 09:47
 **/
public class QueryFollowListModel implements Serializable {
    private List<Integer> customerId;
    private Integer status;
    private String info;
    private Integer creatorId;
    private Date createTime;

    public List<Integer> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(List<Integer> customerId) {
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
