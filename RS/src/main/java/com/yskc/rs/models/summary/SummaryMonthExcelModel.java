package com.yskc.rs.models.summary;

public class SummaryMonthExcelModel {
    private String pname;
    private String workshop;
    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sept;
    private String oct;
    private String nov;
    private String dec;
    private String rdFunds;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getFeb() {
        return feb;
    }

    public void setFeb(String feb) {
        this.feb = feb;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getSept() {
        return sept;
    }

    public void setSept(String sept) {
        this.sept = sept;
    }

    public String getOct() {
        return oct;
    }

    public void setOct(String oct) {
        this.oct = oct;
    }

    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(String rdFunds) {
        this.rdFunds = rdFunds;
    }

    public SummaryMonthExcelModel() {
    }

    public SummaryMonthExcelModel(String pname, String workshop) {
        this.pname = pname;
        this.workshop = workshop;
    }

    public SummaryMonthExcelModel(String pname, String workshop, String jan, String feb, String mar, String apr, String may, String jun, String jul, String aug, String sept, String oct, String nov, String dec, String rdFunds) {
        this.pname = pname;
        this.workshop = workshop;
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sept = sept;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
        this.rdFunds = rdFunds;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }
}
