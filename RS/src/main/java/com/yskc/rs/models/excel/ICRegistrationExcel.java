package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/2 15:24
 * @Description:
 * @author: hsx
 */
public class ICRegistrationExcel implements Serializable {

    @Excel(name = "登记号", order = 0, fieldName = "registerNo")
    private String registerNo;      //登记号

    @Excel(name = "设计名称", order = 1, fieldName = "desginName")
    private String desginName;       //设计名称

    @Excel(name = "申请日期", order = 2, fieldName = "applyDate")
    private Date applyDate;         //申请日期

    @Excel(name = "权利人名称", order = 3, fieldName = "ownerName")
    private String ownerName;      //权利人名称

    @Excel(name = "权利人地址", order = 4, fieldName = "ownerAddress")
    private String ownerAddress;      //权利人地址

    @Excel(name = "颁证日期", order = 5, fieldName = "issueDate")
    private Date issueDate;         //颁证日期

    @Excel(name = "证书号", order = 6, fieldName = "certificateNo")
    private String certificateNo;       //证书号

    @Excel(name = "来源", order = 7, fieldName = "source")
    private String source;

    public String getDesginName() {
        return desginName;
    }

    public void setDesginName(String desginName) {
        this.desginName = desginName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
