package com.yskc.rs.models.employee;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: wangxing
 * @CreateTime: 2019-11-16 10:41
 * @Description: QueryEmployeeModel
 */
public class QueryEmployeeModel {
    private Integer companyId;
    private String enumber;
    private String ename;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
