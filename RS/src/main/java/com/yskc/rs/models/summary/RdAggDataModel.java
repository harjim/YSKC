package com.yskc.rs.models.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.*;

/**
 * 研发费用model
 *
 * @author zhangdingfu
 */
public class RdAggDataModel {

    private Integer id;

    private String rdNumber;

    private String month;

    private String pname;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Map<String, BigDecimal> totalFunds = new LinkedHashMap<>();

    private Boolean trialProd;

    private Date tBeginDate;
    private Date tEndDate;

    public RdAggDataModel() {
    }

    public RdAggDataModel(String month) {
        this.month = month;
    }

    public RdAggDataModel(Integer id, String rdNumber, String month,
                          String pname, Boolean trialProd,Date tBeginDate,Date tEndDate) {
        this.id = id;
        this.rdNumber = rdNumber;
        this.month = month;
        this.pname = pname;
        this.trialProd = trialProd;
        this.tBeginDate = tBeginDate;
        this.tEndDate = tEndDate;
    }

    public Date gettBeginDate() {
        return tBeginDate;
    }

    public void settBeginDate(Date tBeginDate) {
        this.tBeginDate = tBeginDate;
    }

    public Date gettEndDate() {
        return tEndDate;
    }

    public void settEndDate(Date tEndDate) {
        this.tEndDate = tEndDate;
    }

    public Boolean getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(Boolean trialProd) {
        this.trialProd = trialProd;
    }

    public Map<String, BigDecimal> getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(Map<String, BigDecimal> totalFunds) {
        this.totalFunds = totalFunds;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
