package com.yskc.rs.models.reportform;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 项目管理指标model
 * @author: cyj
 * @create: 2022-01-19 16:43
 **/
public class RdManageIndexModel implements Serializable {
    //财务规划研发费/业务预测研发费
    private String rdFeeSales;
    //研发费总额/财务规划研发费
    private String amountIRdFee;
    //研发费总额/业务预测研发费
    private String amountISalesFee;
    //研发费总额/累计营收入
    private String amountIRevenue;
    //可加计扣除研发费/业务预测研发费
    private String exAmountIFcst;
    //可加计扣除研发费/总营收
    private String exAmountIRevunue;
    //原材料费/研发费用总额
    private String materialIRdFunds;
    //研发费同期比
    private String amountIncrease;
    //总研发费与上年比
    private String totalAmountIncrease;
    //研发人员/人员总数
    private String member;

    public String getRdFeeSales() {
        return rdFeeSales;
    }

    public void setRdFeeSales(String rdFeeSales) {
        this.rdFeeSales = rdFeeSales;
    }

    public String getAmountIRdFee() {
        return amountIRdFee;
    }

    public void setAmountIRdFee(String amountIRdFee) {
        this.amountIRdFee = amountIRdFee;
    }

    public String getAmountISalesFee() {
        return amountISalesFee;
    }

    public void setAmountISalesFee(String amountISalesFee) {
        this.amountISalesFee = amountISalesFee;
    }

    public String getAmountIRevenue() {
        return amountIRevenue;
    }

    public void setAmountIRevenue(String amountIRevenue) {
        this.amountIRevenue = amountIRevenue;
    }

    public String getExAmountIFcst() {
        return exAmountIFcst;
    }

    public void setExAmountIFcst(String exAmountIFcst) {
        this.exAmountIFcst = exAmountIFcst;
    }

    public String getExAmountIRevunue() {
        return exAmountIRevunue;
    }

    public void setExAmountIRevunue(String exAmountIRevunue) {
        this.exAmountIRevunue = exAmountIRevunue;
    }

    public String getMaterialIRdFunds() {
        return materialIRdFunds;
    }

    public void setMaterialIRdFunds(String materialIRdFunds) {
        this.materialIRdFunds = materialIRdFunds;
    }

    public String getAmountIncrease() {
        return amountIncrease;
    }

    public void setAmountIncrease(String amountIncrease) {
        this.amountIncrease = amountIncrease;
    }

    public String getTotalAmountIncrease() {
        return totalAmountIncrease;
    }

    public void setTotalAmountIncrease(String totalAmountIncrease) {
        this.totalAmountIncrease = totalAmountIncrease;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
