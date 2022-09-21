package com.yskc.ms.models.checkPayment;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 10:51
 **/
public class PaymentProjectModel {
    private Integer id;
    private Integer checkPaymentId;
    private String rdTitle;
    private String pname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckPaymentId() {
        return checkPaymentId;
    }

    public void setCheckPaymentId(Integer checkPaymentId) {
        this.checkPaymentId = checkPaymentId;
    }

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
}
