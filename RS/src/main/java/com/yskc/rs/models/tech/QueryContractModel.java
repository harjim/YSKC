package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/20 9:08
 * @Description:
 */
public class QueryContractModel extends PageParams {

    private Integer id;

    private String ename;

    private String emodal;

    private String contractNo;

    private Integer investmentId;

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}
