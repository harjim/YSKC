package com.yskc.ms.models.company;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/12/4 17:07
 * description:银行账户信息
 */
public class BankInfoModel implements Serializable {

    private Integer id;
    private Integer year;
    private String bankName;//开户行
    private String bankAccount;//银行账户
    private String accountName;//开户名
    private Integer creditRating;//信用等级 ,0:AAA,1:AA,2:A,3:BBB,4:BB,5:B,6:CCC,7:CC,8:C,9:D
    private String industry;//企业领域
    private Integer companyId;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(Integer creditRating) {
        this.creditRating = creditRating;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
