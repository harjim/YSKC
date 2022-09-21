package com.yskc.ms.models.rs;


import java.io.Serializable;

/**
 * Created by hck
 * on 2020/10/15 13:38
 * description:技改项目model
 */
public class RsTechProjectModel implements Serializable {

    private Integer id;//项目id

    private Integer deptId;

    private String deptName;//所属部门

    private Integer companyId;


    private String companyName;//客户名称

    private Integer pyear;//项目年份

    private Integer productId;//产品类型id


    private String direction;

    private Integer tproductId;//申报项目id

    private Integer tProjectId;//技改项目id
    private String productName;//项目名称
    private Integer year;//技改项目年份

    private Boolean hasUsed;//是否已上传文件

    private String addressCode;//地区

    private Integer reportYear;//申报项目年份

    /**
     * 是否备案
     */
    private Boolean beian;

    public Integer getReportYear() {
        return reportYear;
    }

    public void setReportYear(Integer reportYear) {
        this.reportYear = reportYear;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Integer gettProjectId() {
        return tProjectId;
    }

    public void settProjectId(Integer tProjectId) {
        this.tProjectId = tProjectId;
    }

    public Boolean getHasUsed() {
        return hasUsed;
    }

    public void setHasUsed(Boolean hasUsed) {
        this.hasUsed = hasUsed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getPyear() {
        return pyear;
    }

    public void setPyear(Integer pyear) {
        this.pyear = pyear;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTproductId() {
        return tproductId;
    }

    public void setTproductId(Integer tproductId) {
        this.tproductId = tproductId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean getBeian() {
        return beian;
    }

    public void setBeian(Boolean beian) {
        this.beian = beian;
    }
}
