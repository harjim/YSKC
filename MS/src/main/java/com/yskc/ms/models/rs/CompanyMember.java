package com.yskc.ms.models.rs;


/**
 * 成员公司对象
 * @author huronghua
 */
public class CompanyMember {
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 公司名称
     */
    private String companyName;
    private String companyLogoPath;

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}