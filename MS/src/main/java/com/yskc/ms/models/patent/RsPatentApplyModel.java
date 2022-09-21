package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/7/1 16:06
 * description:
 */
public class RsPatentApplyModel implements Serializable {

    private int id;
    /**
     * 专利号
     */
    private String patentNo;
    /**
     * 申请人
     */
    private String applyName;
    /**
     * 地址
     */
    private String address;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
