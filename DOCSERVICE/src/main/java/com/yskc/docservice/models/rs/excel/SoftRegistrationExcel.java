package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/2 15:25
 * @Description:
 * @author: hsx
 */
public class SoftRegistrationExcel implements Serializable {

    @Excel(name = "登记号", order = 0, fieldName = "registerNo")
    private String registerNo;      //登记号

    @Excel(name = "软件名称", order = 1, fieldName = "softName")
    private String softName;       //软件名称

    @Excel(name = "软件著作人", order = 2, fieldName = "ownerName")
    private String ownerName;      //软件著作人

    @Excel(name = "发布日期", order = 3, fieldName = "issueDate")
    private Date issueDate;         //发布日期

    @Excel(name = "证书号", order = 4, fieldName = "certificateNo")
    private String certificateNo;       //证书号

    @Excel(name = "来源", order = 5, fieldName = "source")
    private String source;


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
