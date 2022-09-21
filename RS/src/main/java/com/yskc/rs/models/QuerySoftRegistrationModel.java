package com.yskc.rs.models;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/2 16:24
 * @Description:
 * @author: hsx
 */
public class QuerySoftRegistrationModel extends PageParams implements Serializable {

    private String softName;       //软件名称

    private String ownerName;      //软件著作人

    private String registerNo;      //登记号

    private String certificateNo;       //证书号

    private Integer companyId;

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
