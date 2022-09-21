package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;

/**
 * @program: doc-service
 * @description:
 * @author: wjy
 * @create: 2022-08-27 08:10
 **/
public class SignatureExcel implements Serializable {
    @Excel(name = "项目名称", order =0, fieldName = "pname")
    private String pname;
    @Excel(name = "项目RD", order = 1, fieldName = "rdTitle")
    private String rdTitle;
    @Excel(name = "编制", order = 2, fieldName = "toCompile")
    private String toCompile;
    @Excel(name = "审核", order = 3, fieldName = "auditor")
    private String auditor;
    @Excel(name = "批准", order = 4, fieldName = "approval")
    private String approval;

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getToCompile() {
        return toCompile;
    }

    public void setToCompile(String toCompile) {
        this.toCompile = toCompile;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }
}
