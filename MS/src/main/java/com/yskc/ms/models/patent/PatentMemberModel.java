package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-02-11 10:44
 **/
public class PatentMemberModel implements Serializable {
    private Integer id;
    private Integer memberId;
    private Integer mType;
    private Integer demandId;
    private String memberName;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }
}
