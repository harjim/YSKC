package com.yskc.ms.models.project;

import java.util.List;

public class ProjectInfoModel {
    private int id;
    private Integer customerId;
    private String companyName;
    private String addressCode;
    private Integer year;
    private String productName;
    private Integer deptId;
    private String deptName;
    private String owerName;
    private String businessName;
    private String techRealName;
    private String financeRealName;
    private List<ProjectMemberModel> members;
    private Integer productId;
    private Integer productType;//项目类型

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 客户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
    }

    public List<ProjectMemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<ProjectMemberModel> members) {
        this.members = members;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
