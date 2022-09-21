package com.yskc.rs.models;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;

/**
 * @DateTime: 2021/11/2 16:15
 * @Description:
 * @author: hsx
 */
public class QueryICRegistrationModel extends PageParams implements Serializable {

    private Integer id;

    private String desginName;      //设计名称

    private String registerNo;      //设计登记号   唯一

    private String certificateNo;       //登记证书号   非必填，唯一

    private String ownerName;    //权利人名称

    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesginName() {
        return desginName;
    }

    public void setDesginName(String desginName) {
        this.desginName = desginName;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
