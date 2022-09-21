package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/entity/ms
 * @Author: hm
 * @CreateTime: 2022/9/3
 * @Description: 专利供应商实体
 */
@TableName( "patent_supplier" )
public class PatentSupplierEntity extends MsBaseEntity {
    @TableId
    private Integer id;

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

    /**
     * 备注
     */
    private String remark;

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
}
