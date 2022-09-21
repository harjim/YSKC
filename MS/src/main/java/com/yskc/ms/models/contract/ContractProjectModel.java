package com.yskc.ms.models.contract;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 11:21
 **/
public class ContractProjectModel {
    private Integer id;
    private Integer contractId;
    private Integer customerId;
    private Integer productId;
    private String productName;
    private Integer beginYear;
    private Integer endYear;
    private BigDecimal ratio;
    private Integer signCnt;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getSignCnt() {
        return signCnt;
    }

    public void setSignCnt(Integer signCnt) {
        this.signCnt = signCnt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
