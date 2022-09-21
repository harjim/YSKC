package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.io.Serializable;

@TableName("contractTraceability")
public class ContractTraceability extends MsBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 客户Id
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 封面
     */
    private String thecover;
    /**
     * 二维码地址
     */
    private String qrcode;

    public static ContractTraceability build(String contractNo, Integer customerId, String companyName, String thecover) {
        ContractTraceability ct = new ContractTraceability();
        ct.contractNumber = contractNo;
        ct.customerId = customerId;
        ct.customerName = companyName;
        ct.thecover = thecover;
        return ct;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getThecover() {
        return thecover;
    }

    public void setThecover(String thecover) {
        this.thecover = thecover;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
