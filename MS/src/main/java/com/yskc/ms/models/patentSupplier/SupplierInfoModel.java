package com.yskc.ms.models.patentSupplier;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/patentSupplier
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 供应商基本信息model
 */
public class SupplierInfoModel implements Serializable {
    private Integer id;

    /**
     * 供应商
     */
    @NotBlank(message = "供应商不可为空")
    @Length(min = 0, max = 100, message = "供应商长度不可超过100")
    private String supplier;

    /**
     * 户名
     */
    @NotBlank(message = "户名不可为空")
    @Length(min = 0, max = 100, message = "户名长度不可超过100")
    private String accountName;

    /**
     * 账号
     */
    @NotBlank(message = "账号不可为空")
    @Length(min = 0, max = 80, message = "账号长度不可超过80")
    private String accountNumber;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不可为空")
    @Length(min = 0, max = 80, message = "联系人长度不可超过80")
    private String linkman;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不可为空")
    @Length(min = 0, max = 80, message = "联系电话长度不可超过80")
    private String linkTel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
