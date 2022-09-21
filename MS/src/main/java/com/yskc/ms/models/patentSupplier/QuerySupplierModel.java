package com.yskc.ms.models.patentSupplier;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/patentSupplier
 * @Author: hm
 * @CreateTime: 2022/9/5
 * @Description: 查询专利供应商参数model
 */
public class QuerySupplierModel extends PageParams {

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 户名
     */
    private String accountName;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String linkTel;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }
}
