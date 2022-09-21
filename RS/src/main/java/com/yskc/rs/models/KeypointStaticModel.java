package com.yskc.rs.models;

import java.io.Serializable;

/**
 * @DateTime: 2021/10/11 16:51
 * @Description:
 * @author: hsx
 */
public class KeypointStaticModel implements Serializable {

    private Integer customerCnt;

    private Integer rdCnt;

    private Integer patentCnt;

    private Integer memberCnt;

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
}
