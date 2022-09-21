package com.yskc.rs.models.summary;

import java.math.BigDecimal;
import java.util.List;

public class SubsidiaryLedgerModel {
    private String name;
    private Integer key;
    private List<SummaryMonthModel> summaryMonths;
    private BigDecimal rdFunds;

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public List<SummaryMonthModel> getSummaryMonths() {
        return summaryMonths;
    }

    public void setSummaryMonths(List<SummaryMonthModel> summaryMonths) {
        this.summaryMonths = summaryMonths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public SubsidiaryLedgerModel(String name, Integer key, List<SummaryMonthModel> summaryMonths) {
        this.name = name;
        this.key = key;
        this.summaryMonths = summaryMonths;
    }
}
