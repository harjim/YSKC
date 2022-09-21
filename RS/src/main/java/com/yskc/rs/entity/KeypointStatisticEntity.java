package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/10/9 17:25
 * @Description:
 * @author: hsx
 */
@TableName("keypoint_statistic")
public class KeypointStatisticEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private Integer customerCnt;

    private Integer rdCnt;

    private Integer patentCnt;

    private Integer memberCnt;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerCnt() {
        return customerCnt;
    }

    public void setCustomerCnt(Integer customerCnt) {
        this.customerCnt = customerCnt;
    }

    public Integer getRdCnt() {
        return rdCnt;
    }

    public void setRdCnt(Integer rdCnt) {
        this.rdCnt = rdCnt;
    }

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
