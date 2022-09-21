package com.yskc.ms.models.checkPayment;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 16:17
 **/
public class PaymentAuditModel {
    private Integer id;
    private Integer nodeNumber;
    private Boolean pass;//true 通过 false 驳回
    private String suggestion;
    private Integer instanceId;
    private String payType;
    private String paymentFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPaymentFile() {
        return paymentFile;
    }

    public void setPaymentFile(String paymentFile) {
        this.paymentFile = paymentFile;
    }
}
